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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.Employee;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.CmachineVo;
import com.mixiusi.bean.vo.MachineBaseInfoVo;
import com.mixiusi.bean.vo.MachineDownVo;
import com.mixiusi.bean.vo.MachineStatusVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.EmployeeService;
import com.mixiusi.service.ErrorRecordService;
import com.mixiusi.service.MachineAddressService;
import com.mixiusi.vo.MachineAndEmployeeVo;
import com.mixiusi.vo.MachineDownResponse;
import com.mixiusi.vo.MachineStatusResponse;

/**
 *咖啡机查询 
 * @author liukun
 *
 */
@RestController
@RequestMapping("/machine")
public class CoffeeMachineController {
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineController.class);
	@Reference
	private CoffeeMachineService coffeeMachineService;
	@Reference
	private EmployeeService employeeService;
	@Reference
	private ErrorRecordService errorRecordService;
	@Reference
	private MachineAddressService machineAddressService;
	@Autowired
	private ApplicationProperties application;
	/**
	 * 根据咖啡机编号查询咖啡机
	 * @param machineCode
	 * @return
	 */
	
	@GetMapping("/getOneMachineInfo")
	public ResultBean getCoffeeMachineInfo(String machineCode) {
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineCode);
		if(null == cm) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		return ResultBean.ok(cm);
	}
	/**
	 * 运维人员负责的咖啡机
	 * @param workerId
	 * @return
	 */
	@GetMapping("/coffeeMachineForEmployee")
	public ResultBean CoffeeMachineForEmployee(String workerId) {
		List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
		if(null == list || list.isEmpty()) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		return ResultBean.ok(list);
	}
	/**
	 * 查询所有咖啡机信息
	 * @return
	 */
	@GetMapping("/getAllMachine")
	public ResultBean getAllCoffeeMachine(CmachineVo mvo) {
		Integer page = mvo.getPage();
		Integer size = mvo.getSize();
		List<CoffeeMachine> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		list = coffeeMachineService.getAllCoffeeMachineCriteria(mvo);
		Long sum = coffeeMachineService.getCoffeeMachineNumber(mvo);
		if(null == list) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		List<MachineAndEmployeeVo> resultList = new ArrayList<>();
		if(!list.isEmpty()) {
			for (CoffeeMachine cm : list) {
				MachineAndEmployeeVo me = new MachineAndEmployeeVo();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				String workerId = cm.getEmployeeCode();
				if(!StringUtils.isNull(workerId)) {
					Employee em = employeeService.queryEmployeeById(workerId);
					if(null != em) {
						me.setRealname(em.getRealname());
					}
				}
			}
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	
	/**
	 * 在新增运维人员的同时跟新绑定的咖啡机信息
	 * @return
	 */
//	@PostMapping("/updateCoffeeMachine")
//	public ResultBean updateCoffeeMachine(List<String> machineCodes, String employeeCode) {
//		List<CoffeeMachine> list = coffeeMachineService.queryMachinesByCode(machineCodes);
//		if(null == list || list.isEmpty()) {
//			return ResultBean.error(MxsConstants.CODE1, "咖啡机不存在--" + machineCodes);
//		}
//		list.stream().forEach(machine -> machine.setEmployeeCode(employeeCode));
//		coffeeMachineService.addCoffeeMachines(list);
//		return ResultBean.ok();
//	}
	
	@GetMapping("/removeMachine")
	public ResultBean removeCoffeeMachine(List<String> machineCodes) {
		boolean flag = coffeeMachineService.removeCoffeeMachines(machineCodes);
		if(!flag) {
			return ResultBean.error();
		}
		return ResultBean.ok();
	}
	/**
	 * 查询咖啡机基本信息
	 * @param baseInfo
	 * @return
	 */
	@GetMapping("/queryBaseInfo")
	public ResultBean getCoffeeBaseInfo(MachineBaseInfoVo baseInfo) {
		Integer page = baseInfo.getPage();
		Integer size = baseInfo.getSize();
		List<CoffeeMachine> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		list = coffeeMachineService.queryMachineBaseInfo(baseInfo);
		Long sum = coffeeMachineService.queryMachineBaseInfoSum(baseInfo);
		if(null == list) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		List<MachineAndEmployeeVo> resultList = new ArrayList<>();
//		List<CoffeeMachine> cmlist = list.getContent();
		if(!list.isEmpty()) {
			for (CoffeeMachine cm : list) {
				MachineAndEmployeeVo me = new MachineAndEmployeeVo();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				String workerId = cm.getEmployeeCode();
				if(!StringUtils.isNull(workerId)) {
					Employee em = employeeService.queryEmployeeById(workerId);
					if(null != em) {
						me.setRealname(em.getRealname());
					}
				}
			}
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	
	/**
	 * 获取咖啡机状态信息
	 * @param statusInfo
	 * @return
	 */
	@GetMapping("/queryStatusInfo")
	public ResultBean getMachineStatus(MachineStatusVo statusInfo) {
		Integer page = statusInfo.getPage();
		Integer size = statusInfo.getSize();
		List<CoffeeMachine> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		list = coffeeMachineService.queryMachineStatus(statusInfo);
		Long sum = coffeeMachineService.queryMachineStatusSum(statusInfo);
		if(null == list) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		List<MachineStatusResponse> resultList = new ArrayList<>();
		if(!list.isEmpty()) {
			for (CoffeeMachine cm : list) {
				MachineStatusResponse me = new MachineStatusResponse();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				String workerId = cm.getEmployeeCode();
				if(!StringUtils.isNull(workerId)) {
					Employee em = employeeService.queryEmployeeById(workerId);
					if(null != em) {
						me.setRealname(em.getRealname());
					}
				}
			}
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	
	/**
	 * 获取咖啡机停机记录和严重缺料记录
	 * @param downInfo
	 * @return
	 */
	@GetMapping("/queryMachineDownRecored")
	public ResultBean getMachineDownRecored(MachineDownVo downInfo) {
		Integer page = downInfo.getPage();
		Integer size = downInfo.getSize();
		List<ErrorRecord> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		list = errorRecordService.queryErrorDown(downInfo);
		Long sum = errorRecordService.queryErrorDownSum(downInfo);
		if(null == list) {
			return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
		}
		List<MachineDownResponse> resultList = new ArrayList<>();
		if(!list.isEmpty()) {
			for (ErrorRecord cm : list) {
				MachineDownResponse me = new MachineDownResponse();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				Employee emp = employeeService.queryEmployeeById(cm.getWorkerId());
				if(null != emp) {
					log.info("员工编号；；；" + emp.getWorkerId());
					me.setRealname(emp.getRealname());
				}
			}
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	/**
	 * 查询没有运营人员负责的咖啡机
	 * @return
	 */
	@GetMapping("/getMachineNoEmployee")
	public ResultBean getMachineNoEmployee() {
		List<CoffeeMachine> list = coffeeMachineService.getAllNoEmployee();
		return ResultBean.ok(list);
	}
	/**
	 * 导出咖啡机基础信息
	 * @param baseInfo
	 * @return
	 */
	@GetMapping("/exportExcelForBaseInfo")
	public ResultBean exportExcel4BaseInfo(MachineBaseInfoVo baseInfo) {
		Integer page = baseInfo.getPage();
		Integer size = baseInfo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MachineBaseInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<MachineAndEmployeeVo> resultList = new ArrayList<>();
		maps.put("machineId", "机器Id");
		maps.put("type", "版本");
		maps.put("downTime", "停机时间");
		maps.put("status", "当前状态");
		maps.put("is_running", "正常运行");
		maps.put("createTime", "创建时间");
		maps.put("updateTime", "更新时间");
		maps.put("repaireTime", "修复时间");
		maps.put("employeeCode", "运维人员编号");
		maps.put("realname", "运维人员姓名");
		maps.put("machineCode", "机器编号");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = coffeeMachineService.queryMachineBaseInfoSum(baseInfo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryBaseInfo(baseInfo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				baseInfo.setPage(i);
				flag = queryBaseInfo(baseInfo, resultList);
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
	 * 查询数据
	 * @param baseInfo
	 * @param resultList
	 * @return
	 * @throws BeansException
	 */
	private boolean queryBaseInfo(MachineBaseInfoVo baseInfo, List<MachineAndEmployeeVo> resultList){
		boolean flag = true;
		List<CoffeeMachine> list = coffeeMachineService.queryMachineBaseInfo(baseInfo);
		if(null == list) {
			return false;
		}
//		List<CoffeeMachine> cmlist = list.getContent();
		int len = list.size();
		if(!list.isEmpty()) {
			for (int i = 0; i < len; i++) {
				CoffeeMachine cm = list.get(i);
				MachineAndEmployeeVo me = new MachineAndEmployeeVo();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				String workerId = cm.getEmployeeCode();
				if(!StringUtils.isNull(workerId)) {
					Employee em = employeeService.queryEmployeeById(workerId);
					if(null != em) {
						me.setRealname(em.getRealname());
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 导出咖啡机状态信息
	 * @param statusInfo
	 * @return
	 */
	@GetMapping("/exportExcelForStatusInfo")
	public ResultBean exportExcel4StatusInfo(MachineStatusVo statusInfo) {
		Integer page = statusInfo.getPage();
		Integer size = statusInfo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MachineStatusInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<MachineStatusResponse> resultList = new ArrayList<>();
		maps.put("machineId", "机器Id");
		maps.put("is_running", "正常运行");
		maps.put("updateTime", "更新时间");
		maps.put("repaireTime", "修复时间");
		maps.put("employeeCode", "运维人员编号");
		maps.put("realname", "运维人员姓名");
		maps.put("machineCode", "机器编号");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = coffeeMachineService.queryMachineStatusSum(statusInfo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryStatusInfo(statusInfo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				statusInfo.setPage(i);
				flag = queryStatusInfo(statusInfo, resultList);
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
	 * 查询咖啡机状态数据数据
	 * @param statusInfo
	 * @param resultList
	 * @return
	 */

	private boolean queryStatusInfo(MachineStatusVo statusInfo, List<MachineStatusResponse> resultList){
		boolean flag = true;
		List<CoffeeMachine> list = coffeeMachineService.queryMachineStatus(statusInfo);
		if(null == list) {
			return false;
		}
		int len = list.size();
		if(!list.isEmpty()) {
			for (int i = 0; i < len; i++) {
				CoffeeMachine cm = list.get(i);
				MachineStatusResponse me = new MachineStatusResponse();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				String workerId = cm.getEmployeeCode();
				if(!StringUtils.isNull(workerId)) {
					Employee em = employeeService.queryEmployeeById(workerId);
					if(null != em) {
						me.setRealname(em.getRealname());
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 导出咖啡机状态信息
	 * @param downInfo
	 * @return
	 */
	@GetMapping("/exportExcelForDownRecored")
	public ResultBean exportExcelForDownRecored(MachineDownVo downInfo) {
		Integer page = downInfo.getPage();
		Integer size = downInfo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "MachineDownRecored.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<MachineDownResponse> resultList = new ArrayList<>();
		maps.put("machineId", "机器Id");
		maps.put("startTime", "开始时间");
		maps.put("endTime", "结束时间");
		maps.put("durationTime", "持续时长");
		maps.put("workerId", "运维人员编号");
		maps.put("realname", "运维人员姓名");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		
		Long sum = errorRecordService.queryErrorDownSum(downInfo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryDownRecored(downInfo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				downInfo.setPage(i);
				flag = queryDownRecored(downInfo, resultList);
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
	 * 查询咖啡机状态数据数据
	 * @param downInfo
	 * @param resultList
	 * @return
	 */
	private boolean queryDownRecored(MachineDownVo downInfo, List<MachineDownResponse> resultList){
		boolean flag = true;
		List<ErrorRecord> list = errorRecordService.queryErrorDown(downInfo);
		if(null == list) {
			return false;
		}
		int len = list.size();
		if(!list.isEmpty()) {
			for (int i = 0; i < len; i++) {
				ErrorRecord cm = list.get(i);
				MachineDownResponse me = new MachineDownResponse();
				resultList.add(me);
				BeanUtils.copyProperties(cm, me);
				Employee emp = employeeService.queryEmployeeById(cm.getWorkerId());
				if(null != emp) {
//					log.info("员工编号{}", emp.getWorkerId());
					me.setRealname(emp.getRealname());
				}
			}
		}
		return flag;
	}
}
