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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.vo.ErrorRecordVo;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.ErrorRecordService;

@RestController
@RequestMapping("/error")
public class ErrorRecordController {
	private final Logger log = LoggerFactory.getLogger(ErrorRecordController.class);
	
	@Reference
	private ErrorRecordService errorRecordService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 条件分页查询
	 * @return
	 */
	@GetMapping(value = "/query")
	public ResultBean query(ErrorRecordVo errorRecordVo) {//, Integer page, Integer size
		Integer page = errorRecordVo.getPage();
		Integer size = errorRecordVo.getSize();
		log.info("条件查询" + errorRecordVo);
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		log.info("条件查询");
		List<ErrorRecord> list = errorRecordService.findErrorCriteria(errorRecordVo);
		Long sum = errorRecordService.findErrorNumber(errorRecordVo);
		if(null == list)
			return ResultBean.error();
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 * 批量删除
	 * @param errorIds
	 * @return
	 */
	@DeleteMapping(value = "/removeError")
	public ResultBean removeError(@RequestBody List<Integer> errorIds) {
		Boolean flag = errorRecordService.removeBatch(errorIds);
		if(flag)
			return ResultBean.ok();
		return ResultBean.error();
	}
	
	@GetMapping(value = "/exportExcelForError")
	public ResultBean exportExcel4Error(ErrorRecordVo errorRecordVo) {
		Integer page = errorRecordVo.getPage();
		Integer size = errorRecordVo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "ErrorInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<ErrorRecord> resultList = new ArrayList<>();
		maps.put("id", "报警记录Id");
		maps.put("machineId", "咖啡机Id");
		maps.put("startTime", "报警开始时间");
		maps.put("endTime", "报警结束时间");
		maps.put("durationTime", "持续时长");
		maps.put("type", "报警类型");
		maps.put("workerId", "负责人员Id");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = errorRecordService.findErrorNumber(errorRecordVo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryError(errorRecordVo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				errorRecordVo.setPage(i);
				flag = queryError(errorRecordVo, resultList);
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
	
	private boolean queryError(ErrorRecordVo errorRecordVo, List<ErrorRecord> resultList){
		boolean flag = true;
		List<ErrorRecord> result = errorRecordService.findErrorCriteria(errorRecordVo);
		if(null == result) {
			flag = false;
		}
		resultList.addAll(result);
		return flag;
	}
	
}
