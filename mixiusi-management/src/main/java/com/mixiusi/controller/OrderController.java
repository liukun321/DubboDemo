package com.mixiusi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.OrderVo;
import com.mixiusi.bean.vo.SaleStatisticVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.PayindentService;

/**
 * 订单查询
 * @author liukun
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	private final Logger log = LoggerFactory.getLogger(OrderController.class);
	@Reference
	private PayindentService payindentService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 查询订单的总金额
	 * @param status 0 待支付；1 支付成功；2 支付失败； 3:退款成功 ；4：退款失败 5 订单作废
	 * 
	 * @param payMethod 支付方式 1：支付宝； 2 微信
	 * @return
	 */
	@GetMapping("/sumprice")
	public ResultBean getSumprice4Order(Integer status, Integer payMethod) {
		Double sum = payindentService.querySumprice(status, payMethod);
		log.info("Sum price:" + sum);
		return ResultBean.ok(sum);
		
	}
	/**
	 * 根据订单ID查询
	 * @param indentId
	 * @return
	 */
	@GetMapping("/queryOrderById")
	public ResultBean queryOrderInfoById(String indentId) {
		Payindent order= payindentService.queryPayindentById(indentId);
		if(null == order)
			return ResultBean.error();
		return ResultBean.ok(order);
		
	}
	/**
	 * 机器端订单管理
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/queryAllOrder")
	public ResultBean queryAllOrderInfo(OrderVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<Payindent> list = payindentService.queryOrder(orderVo);
		Long sum = payindentService.querySumOrder(orderVo);
		if(list == null)
			return ResultBean.error();
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 *  咖啡销售数据
	 * @param ssvo
	 * @return
	 */
	@GetMapping("/querySaleStatistic")
	public ResultBean queryOrderSum(SaleStatisticVo ssvo) {
		Integer page = ssvo.getPage();
		Integer size = ssvo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<Payindent> list = payindentService.querySale(ssvo);
		Long sum = payindentService.querySaleSum(ssvo);
		if(list == null)
			return ResultBean.error();
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 * 导出所有订单
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/exportExcelForOrder")
	public ResultBean exportExcelForOrder(OrderVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MachineOrderInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<Payindent> resultList = new ArrayList<>();
		maps.put("indentId", "订单Id");
		maps.put("machineId", "咖啡机Id");
		maps.put("coffeeId", "咖啡机Id");
		maps.put("discount_price", "咖啡折后价");
		maps.put("priceOri", "咖啡机原价");
		maps.put("price", "咖啡实际价");
		maps.put("payMethod", "订单支付方式");
		maps.put("payStatus", "订单支付方式");
		maps.put("createTime", "订单创建时间");
		maps.put("orderId", "第三方接口Id");
		maps.put("isHot", "温度类型");
		maps.put("sugar", "糖度");
		maps.put("coffeeType", "咖啡浓度");
		maps.put("orderId", "第三方接口Id");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = payindentService.querySumOrder(orderVo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryOrderInfo(orderVo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				orderVo.setPage(i);
				flag = queryOrderInfo(orderVo, resultList);
				if(!flag) {
					log.info("查询数据失败");
					return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
				}
			}
		}
		//3 创建临时文件
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if(!file.exists()) {
			try {
				flag = file.createNewFile();
			} catch (IOException e) {
				log.info(e.getMessage());
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}
		//4 导出EXCEL
		flag = ExcelExportUtils.excelExport(maps, resultList, file);
		if(flag) {
			return ResultBean.ok(application.getDownloadPath() + file.getName());
		}else {
			log.info("导出EXCEL失败");
			return ResultBean.error();
		}
	}
	private boolean queryOrderInfo(OrderVo orderVo, List<Payindent> resultList){
		boolean flag = true;
		List<Payindent> result = payindentService.queryOrder(orderVo);
		if(null == result) {
			flag = false;
		}
		resultList.addAll(result);
		return flag;
	}
	
	@GetMapping("/exportExcelForSaleStatistic")
	public ResultBean exportExcel4SaleStatistic(SaleStatisticVo ssvo) {
		Integer page = ssvo.getPage();
		Integer size = ssvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MachineSaleStatistic.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<Payindent> resultList = new ArrayList<>();
		maps.put("indentId", "订单Id");
		maps.put("machineId", "咖啡机Id");
		maps.put("coffeeId", "咖啡机Id");
		maps.put("discount_price", "咖啡折后价");
		maps.put("priceOri", "咖啡机原价");
		maps.put("price", "咖啡实际价");
		maps.put("payMethod", "订单支付方式");
		maps.put("payStatus", "订单支付方式");
		maps.put("createTime", "订单创建时间");
		maps.put("orderId", "第三方接口Id");
		maps.put("payStatus", "支付状态");
		maps.put("isHot", "温度类型");
		maps.put("sugar", "糖度");
		maps.put("coffeeType", "咖啡浓度");
		maps.put("orderId", "第三方接口Id");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = payindentService.querySaleSum(ssvo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = querySaleStatistic(ssvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				ssvo.setPage(i);
				flag = querySaleStatistic(ssvo, resultList);
				if(!flag) {
					log.info("查询数据失败");
					return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
				}
			}
		}
		//3 创建临时文件
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if(!file.exists()) {
			try {
				flag = file.createNewFile();
			} catch (IOException e) {
				log.info(e.getMessage());
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}
		//4 导出EXCEL
		flag = ExcelExportUtils.excelExport(maps, resultList, file);
		if(flag) {
			return ResultBean.ok(application.getDownloadPath() + file.getName());
		}else {
			log.info("导出EXCEL失败");
			return ResultBean.error();
		}
	}
	private boolean querySaleStatistic(SaleStatisticVo ssvo, List<Payindent> resultList){
		boolean flag = true;
		List<Payindent> result = payindentService.querySale(ssvo);
		if(null == result) {
			flag = false;
		}
		resultList.addAll(result);
		return flag;
	}
	
}
