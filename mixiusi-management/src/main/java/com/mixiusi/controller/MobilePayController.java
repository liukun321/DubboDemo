package com.mixiusi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeList;
import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.bean.vo.MobilePayVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.MobileCoffeelistService;
import com.mixiusi.service.MobilePayService;
import com.mixiusi.vo.CoffeeListVo;
import com.mixiusi.vo.MobilePayBaseVo;

/**
 * 订单查询
 * @author liukun
 *
 */
@RestController
@RequestMapping("/wechat")
public class MobilePayController {
	private final Logger log = LoggerFactory.getLogger(MobilePayController.class);
	@Reference
	private MobilePayService mobilePayService;
	@Reference
	private MobileCoffeelistService mobileCoffeelistService;
	
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
	public ResultBean getSumprice4Order() {
		Double sum = mobilePayService.querySumprice();
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
		MobilePay order= mobilePayService.queryMobilePayById(indentId);
		if(null == order)
			return ResultBean.error();
		return ResultBean.ok(order);
		
	}
	/**
	 * 小程序端订单管理  
	 * 用户订单详情
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/queryAllOrder")
	public ResultBean queryAllOrderInfo(MobilePayVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		List<MobilePayBaseVo> resultList = new ArrayList<>();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<MobilePay> list = mobilePayService.queryMobilePay(orderVo);
		Long sum = mobilePayService.querySumNumber(orderVo);
		if(list == null)
			return ResultBean.error();
		for (MobilePay mp : list) {
			MobilePayBaseVo mpb = new MobilePayBaseVo();
			resultList.add(mpb);
			BeanUtils.copyProperties(mp, mpb);
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	/**
	 * 微信订单详情
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/queryOrderDetail")
	public ResultBean queryOrderDetail(MobilePayDetailVo mpdvo) {
		Integer page = mpdvo.getPage();
		Integer size = mpdvo.getSize();
		String indentId = mpdvo.getIndentId();
		if(StringUtils.isNull(indentId)) {
			return ResultBean.error(MxsConstants.CODE1, "Id of mobilePay is null");
		}
		List<CoffeeListVo> resultList = new ArrayList<>();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<MobileCoffeeList> list = mobileCoffeelistService.queryMobilePayDetail(mpdvo);
		Long sum = mobileCoffeelistService.queryDetailSumNumber(mpdvo);
		if(list == null)
			return ResultBean.error();
		for (MobileCoffeeList mp : list) {
			CoffeeList clist = mp.getCid();
			CoffeeListVo cls = new CoffeeListVo();
			cls.setIndentId(indentId);
			resultList.add(cls);
			BeanUtils.copyProperties(clist, cls);
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	
	@PostMapping("/updateOrder")
	public ResultBean updateOrder(String indentId, Boolean over) {
		MobilePay mp = mobilePayService.queryMobilePayById(indentId);
		if(null == mp) {
			return ResultBean.error();
		}else {
			mp.setOver(over);
			mobilePayService.updateOrder(mp);
		}
		return ResultBean.ok();
	}
	
	@GetMapping("/deleteOrder")
	public ResultBean removeOrder(List<String> indentIds) {
		boolean flag = mobilePayService.removeMobilePay(indentIds);
		if(flag) 
			return ResultBean.ok();
		return ResultBean.error();
	}
	
	/**
	 * 用户订单详情
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/queryUserOrder")
	public ResultBean queryUserOrderInfo(MobilePayVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<MobilePay> list = mobilePayService.queryMobilePay(orderVo);
		Long sum = mobilePayService.querySumNumber(orderVo);
		if(list == null)
			return ResultBean.error();
		return ResultBean.ok(size, page, sum, list);
	}
	/**
	 * 导出微信订单基本信息
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/exportAllOrderInfo")
	public ResultBean exportAllOrderInfo(MobilePayVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "WechatOrderInfo.xlsx";
		File file = new File(filePath);
		List<MobilePayBaseVo> resultList = new ArrayList<>();
		Map<String, String> maps = new HashMap<>();
		maps.put("indentId", "订单Id");
		maps.put("machineId", "取货的咖啡机Id");
		maps.put("openId", "用户时间");
		maps.put("credit", "订单积分");
		maps.put("payTime", "支付时间");
		maps.put("finishTime", "订单完成时间");
		maps.put("totalPrice", "订单总价");
		maps.put("totalCup", "咖啡机总数量");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = mobilePayService.querySumNumber(orderVo);
		if(sum <= size) {
			flag = queryAllOrderInfo(orderVo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				orderVo.setPage(i);
				flag = queryAllOrderInfo(orderVo, resultList);
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
	
	private boolean queryAllOrderInfo(MobilePayVo orderVo, List<MobilePayBaseVo> resultList){
		boolean flag = true;
		List<MobilePay> list = mobilePayService.queryMobilePay(orderVo);
		if(null == list) {
			return false;
		}
		int len = list.size();
		if(!list.isEmpty()) {
			for (int i = 0; i < len; i++) {
				MobilePay mp = list.get(i);
				MobilePayBaseVo mpb = new MobilePayBaseVo();
				resultList.add(mpb);
				BeanUtils.copyProperties(mp, mpb);
			}
		}
		return flag;
	}
	
	/**
	 * 微信订单详情
	 * @param mpdvo
	 * @return
	 */
	@GetMapping("/exportOrderDetail")
	public ResultBean exportOrderDetail(MobilePayDetailVo mpdvo) {
		Integer page = mpdvo.getPage();
		Integer size = mpdvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "WechatOrderDetail.xlsx";
		File file = new File(filePath);
		List<CoffeeListVo> resultList = new ArrayList<>();
		Map<String, String> maps = new HashMap<>();
		maps.put("indentId", "订单Id");
		maps.put("coffeeId", "咖啡Id");
		maps.put("sugar", "糖度");
		maps.put("cupNum", "杯数");
		maps.put("priceOri", "咖啡机原价");
		maps.put("discount", "是否打折");
		maps.put("discount_price", "折后价");
		maps.put("price", "实际价");
		maps.put("coffeeType", "咖啡浓度");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = mobileCoffeelistService.queryDetailSumNumber(mpdvo);
		if(sum <= size) {
			flag = queryOrderDetail(mpdvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				mpdvo.setPage(i);
				flag = queryOrderDetail(mpdvo, resultList);
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
	
	private boolean queryOrderDetail(MobilePayDetailVo mpdvo, List<CoffeeListVo> resultList){
		boolean flag = true;
		String indentId = mpdvo.getIndentId();
		List<MobileCoffeeList> cmlist = mobileCoffeelistService.queryMobilePayDetail(mpdvo);
		if(null == cmlist) {
			return false;
		}
		int len = cmlist.size();
		if(!cmlist.isEmpty()) {
			for (int i = 0; i < len; i++) {
				MobileCoffeeList mp = cmlist.get(i);
				CoffeeList clist = mp.getCid();
				CoffeeListVo cls = new CoffeeListVo();
				cls.setIndentId(indentId);
				resultList.add(cls);
				BeanUtils.copyProperties(clist, cls);
			}
		}
		return flag;
	}
	/**
	 * 导出微信端用户订单信息
	 * @param orderVo
	 * @return
	 */
	@GetMapping("/exportUserOrderInfo")
	public ResultBean exportUserOrderInfo(MobilePayVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "WechatUserOrderInfo.xlsx";
		File file = new File(filePath);
		List<MobilePay> resultList = new ArrayList<>();
		Map<String, String> maps = new HashMap<>();
		maps.put("indentId", "订单Id");
		maps.put("machineId", "取货的咖啡机Id");
		maps.put("openId", "用户时间");
		maps.put("credit", "订单积分");
		maps.put("payTime", "支付时间");
		maps.put("finishTime", "订单完成时间");
		maps.put("totalPrice", "订单总价");
		maps.put("over", "是否取货");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = mobilePayService.querySumNumber(orderVo);
		if(sum <= size) {
			flag = queryUserOrderInfo(orderVo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				orderVo.setPage(i);
				flag = queryUserOrderInfo(orderVo, resultList);
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
	
	private boolean queryUserOrderInfo(MobilePayVo orderVo, List<MobilePay> resultList){
		boolean flag = true;
		List<MobilePay> result = mobilePayService.queryMobilePay(orderVo);
		if(null == result) {
			flag = false;
		}
		resultList.addAll(result);
		return flag;
	}
}
