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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.MobilePayVo;
import com.mixiusi.bean.vo.WechatUserVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.MobilePayService;
import com.mixiusi.service.UserCouponsService;
import com.mixiusi.service.WechatUserService;
import com.mixiusi.vo.MobileUserVo;

/**
 * 用户controller
 * @author liukun
 *
 */
@RestController
@RequestMapping("/wechatUser")
public class WechatUserController {
	private static Logger log = LoggerFactory.getLogger(WechatUserController.class);
	@Reference
	private WechatUserService wechatUserService;
	@Reference
	private UserCouponsService userCouponsService;
	@Reference
	private MobilePayService mobilePayService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 获取所有用户信息列表
	 * @return
	 * 
	 * 	totoalordernumber //用户订单总数
     	totalPrice//总计消费金额
     	couponnumber//优惠券数量
	 */
	@GetMapping("/getAll")
	public ResultBean list(WechatUserVo uvo) {
		Integer page = uvo.getPage();
		Integer size = uvo.getSize();
		List<WechatUser> list = null;
		List<MobileUserVo> resultList = new ArrayList<>();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
    	list = wechatUserService.findAll(uvo);
    	if(null == list)
    		return ResultBean.error();
    	Long sum = wechatUserService.findUserNumber(uvo);
    	for (WechatUser wechatUser : list) {
    		MobileUserVo userVo = new MobileUserVo();
    		resultList.add(userVo);
    		BeanUtils.copyProperties(wechatUser, userVo);
			String openId = wechatUser.getOpenId();
			Long couponnumber = userCouponsService.findCouponsNumber(openId);
			MobilePayVo mvo = new MobilePayVo();
			mvo.setOpenId(openId);
			Long totoalordernumber = mobilePayService.querySumNumber(mvo);
			Double totalPrice = mobilePayService.queryUserSumprice(openId);
			userVo.setCouponnumber(couponnumber);
			userVo.setTotalPrice(totalPrice);
			userVo.setTotoalordernumber(totoalordernumber);
		}
    	
    	return ResultBean.ok(size, page, sum, resultList);
	}
	/**
	 * 删除用户信息同时，删除用户权限表中的信息
	 * @param wvo
	 * @return
	 */
	@GetMapping("/queryUserCoupons")
	public ResultBean queryUserCoupons(WechatUserVo wvo) {
		Integer page = wvo.getPage();
		Integer size = wvo.getSize();
		List<UserCoupons> list = null;
		List<Coupons> resultList = new ArrayList<>();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
    	list = userCouponsService.findAllCoupons(wvo);
    	Long sum = userCouponsService.findCouponsNumber(wvo.getOpenId());
    	if(null == list)
    		return ResultBean.error();
    	for (UserCoupons userCoupons : list) {
    		resultList.add(userCoupons.getCid());
		}
    	return ResultBean.ok(size, page, sum, resultList);
	}
	
	/**
	 * 导出微信端用户的优惠券信息
	 * @param wvo
	 * @return
	 */
	@GetMapping("/exportExcelForCoupons")
	public ResultBean exportExcelForCoupons(WechatUserVo wvo) {
		Integer page = wvo.getPage();
		Integer size = wvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "WechatCouponsInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<Coupons> resultList = new ArrayList<>();
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
		Long sum = userCouponsService.findCouponsNumber(wvo.getOpenId());
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryCouponsInfo(wvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				wvo.setPage(i);
				flag = queryCouponsInfo(wvo, resultList);
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
	
	private boolean queryCouponsInfo(WechatUserVo wvo, List<Coupons> resultList){
		boolean flag = true;
		List<UserCoupons> uclust = userCouponsService.findAllCoupons(wvo);
		if(null == uclust) {
			return false;
		}
    	for (UserCoupons userCoupons : uclust) {
    		resultList.add(userCoupons.getCid());
		}
		return flag;
	}
}
