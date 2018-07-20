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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.CouponsVo;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.CouponVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.CouponsService;
import com.mixiusi.service.UserCouponsService;
import com.mixiusi.vo.Coupons2User;

@RestController
@RequestMapping("/coupons")
public class CouponsController {
	private final Logger log = LoggerFactory.getLogger(CouponsController.class);
	@Reference
	private CouponsService couponsService;
	@Reference
	private UserCouponsService userCouponsService;
	@Autowired
	private ApplicationProperties application;
	/**
	 * 添加优惠券
	 * @param couponsVo
	 * @return
	 */
	@PostMapping("/addCoupons")
	public ResultBean addCoupons(@RequestBody CouponsVo couponsVo) {
		Integer type = couponsVo.getType();
		if(StringUtils.isNull(type)) {
			return ResultBean.error(MxsConstants.CODE1, "优惠券type不能为空");
		}
		boolean flag = couponsService.addCoupons(couponsVo);
		if(flag) {
			return ResultBean.ok();
		}else {
			return ResultBean.error();
		}
	}
	/**
	 * 删除优惠券
	 * @param couponsCode
	 * @return
	 */
	@DeleteMapping("/removeCoupons")
	public ResultBean removeCoupons(@RequestBody List<String> couponsCodes) {
		boolean flag = couponsService.removeCouponsByCode(couponsCodes);
		if(flag) 
			return ResultBean.ok();
		return ResultBean.error();
	}
	/**
	 * 获取所有的优惠券列表/或者根据类型查询
	 * @param type
	 * @param page 页数从0开始计数
	 * @param size 每页显示的数量
	 * @return
	 */
	
	@GetMapping("/getAllCoupons")
	public ResultBean getAllCoupons(CouponVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		List<Coupons> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		list = couponsService.queryCoupons(cvo);
		Long sum = couponsService.queryCouponsNumber(cvo);
		if(null == list) {
			return ResultBean.error();
		}
		List<Coupons2User> resultList = new ArrayList<>();
		for (Coupons coupons : list) {
			UserCoupons userCoupons = userCouponsService.queryCouponsById(coupons);
			Coupons2User cuser = new Coupons2User();
			if(null != userCoupons) {
				String openId = userCoupons.getUid().getOpenId();
				cuser.setOpenId(openId);
			}
			resultList.add(cuser);
			BeanUtils.copyProperties(coupons, cuser);
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	/**
	 * 导出EXCEL表格
	 * @param cvo
	 * @return
	 */
	@GetMapping("/exportExcelForCoupons")
	public ResultBean exportExcelForCoupons(CouponVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "CouponsInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<Coupons2User> resultList = new ArrayList<>();
		maps.put("openId", "微信端用户Id");
		maps.put("type", "优惠券类型");
		maps.put("value", "优惠券值");
		maps.put("endTime", "失效时间");
		maps.put("couponCode", "优惠券码");
		maps.put("is_use", "是否使用");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = couponsService.queryCouponsNumber(cvo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryCouponsInfo(cvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				cvo.setPage(i);
				flag = queryCouponsInfo(cvo, resultList);
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
	/**
	 * 
	 * @param cvo
	 * @param resultList
	 * @return
	 */
	private boolean queryCouponsInfo(CouponVo cvo, List<Coupons2User> resultList){
		boolean flag = true;
		List<Coupons> list = couponsService.queryCoupons(cvo);
		if(null == list) {
			return false;
		}
//		List<Coupons> cmlist = list.getContent();
		int len = list.size();
		if(!list.isEmpty()) {
			for (int i = 0; i < len; i++) {
				Coupons coupons = list.get(i);
				UserCoupons userCoupons = userCouponsService.queryCouponsById(coupons);
				Coupons2User cuser = new Coupons2User();
				if(null != userCoupons) {
					String openId = userCoupons.getUid().getOpenId();
					cuser.setOpenId(openId);
				}
				resultList.add(cuser);
				BeanUtils.copyProperties(coupons, cuser);
			}
		}
		return flag;
	}
	
}
