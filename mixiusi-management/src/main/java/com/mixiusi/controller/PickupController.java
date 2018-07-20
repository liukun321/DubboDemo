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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Pickup;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.PickupVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.PickupService;

@RestController
@RequestMapping("/pickup")
public class PickupController {
	private final Logger log = LoggerFactory.getLogger(PickupController.class);
	@Reference
	private PickupService pickupService;
	@Autowired
	private ApplicationProperties application;
	
	/**
	 */
	@PostMapping("/updatePickup")
	public ResultBean updatePickup(PickupVo employee) {
		
		
		return ResultBean.ok();
	}
	/**
	 * 分页查询所有的运营人员信息
	 * @return
	 */
	@GetMapping("/getAll")
	public ResultBean getAllPickup(PickupVo pvo) { 
		Integer page = pvo.getPage();
		Integer size = pvo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<Pickup> list = pickupService.getAllPickup(pvo);
		Long sum = pickupService.getAllPickupNumber(pvo);
		if(null == list) {
			return  ResultBean.error();
		}
		return ResultBean.ok(size, page, sum, list);
	}
	
	@GetMapping("/removePickup")
	public ResultBean removePickup(List<String> indentIds) { 
		Boolean flag = pickupService.removePickup(indentIds);
		if(flag) 
			return ResultBean.ok();
		return ResultBean.error();
	}
	
	/**
	 * 取货信息导出
	 * @param pvo
	 * @return
	 */
	@GetMapping("/exportExcelForPickup")
	public ResultBean exportExcel4Pickup(PickupVo pvo) { 
		Integer page = pvo.getPage();
		Integer size = pvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "PickupInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<Pickup> resultList = new ArrayList<>();
		maps.put("indentId", "订单Id");
		maps.put("openId", "微信用户Id");
		maps.put("pickupCode", "取货码");
		maps.put("createTime", "订单创建时间");
		maps.put("isUse", "是否使用");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = pickupService.getAllPickupNumber(pvo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryPickup(pvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				pvo.setPage(i);
				flag = queryPickup(pvo, resultList);
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

	private boolean queryPickup(PickupVo pvo, List<Pickup> resultList){
		boolean flag = true;
		List<Pickup> result = pickupService.getAllPickup(pvo);
		if(null != result) {
			if(null != result)
				resultList.addAll(result);
		}else {
			flag = false;
		}
		return flag;
	}
}
