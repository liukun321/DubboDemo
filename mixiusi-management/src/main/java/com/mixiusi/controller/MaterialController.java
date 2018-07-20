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
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.CoffeeMaterialVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.CoffeeMaterialService;

@RestController
@RequestMapping("/material")
public class MaterialController {
	
	private final Logger log = LoggerFactory.getLogger(MaterialController.class);
	@Reference
	private CoffeeMaterialService coffeeMaterialService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 分页查询
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/queryCoffeeMaterial")
	public ResultBean query(CoffeeMaterialVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<CoffeeMaterial> list = coffeeMaterialService.queryCoffeeMaterial(cvo);
		Long sum = coffeeMaterialService.queryCoffeeMaterialSum(cvo);
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 * 导出咖啡机物料信息
	 * @param cvo
	 * @return
	 */
	@GetMapping("/exportExcelForMaterial")
	public ResultBean exportExcel4Material(CoffeeMaterialVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MaterialInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<CoffeeMaterial> resultList = new ArrayList<>();
		maps.put("id", "物料Id");
		maps.put("stackNumber", "料盒编号");
		maps.put("machineId", "咖啡机Id");
		maps.put("category", "物料种类");
		maps.put("brand", "物料品牌");
		maps.put("status", "物料状态");
		maps.put("dangerTime", "预警时间");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = coffeeMaterialService.queryCoffeeMaterialSum(cvo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryError(cvo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				cvo.setPage(i);
				flag = queryError(cvo, resultList);
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
	
	private boolean queryError(CoffeeMaterialVo cvo, List<CoffeeMaterial> resultList){
		boolean flag = true;
		List<CoffeeMaterial> result = coffeeMaterialService.queryCoffeeMaterial(cvo);
		if(null == result) {
			flag = false;
		}
		resultList.addAll(result);
		return flag;
	}
}
