package com.mixiusi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.Employee;
import com.mixiusi.bean.EmployeeMachines;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.bean.utils.MD5;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtil;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.utils.excel.ExcelExportUtils;
import com.mixiusi.bean.vo.WorkerFeedbackVo;
import com.mixiusi.bean.vo.WorkerVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.EmployeeMachinesService;
import com.mixiusi.service.EmployeeService;
import com.mixiusi.service.WorkerFeedbackService;
import com.mixiusi.vo.EmployeeVo;
import com.mixiusi.vo.ExportEmployee;

@RestController
@RequestMapping("/worker")
public class EmployeeController {
	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Reference
	private EmployeeService employeeService;
	@Reference
	private CoffeeMachineService coffeeMachineService;
	@Reference
	private WorkerFeedbackService workerFeedbackService;
	@Reference
	private EmployeeMachinesService employeeMachinesService;
	private String WORKER_PASSWORD = "123456";
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 1 运营人员新增和删除
	 * 2运营人员信息更改
	 * 3 统计运营人员的绩效，即造成咖啡机报警和停机的总时间
	 * 4运营人员查看负责的咖啡机信息
	 */
	@PostMapping("/addEmployee")
	public ResultBean insertEmployee(@RequestBody EmployeeVo employee) {
		String phoneNumber = employee.getPhoneNumber();
		Map<String, Integer> maps = employee.getMachines();
		if(StringUtil.isPhoneNumber(phoneNumber) || !maps.isEmpty()) {
			try {
				Employee empl = new Employee();
				empl.setPassword(MD5.md5(WORKER_PASSWORD));
				BeanUtils.copyProperties(employee, empl);
				String employeeCode = UUID.randomUUID().toString().replaceAll("-", "");
				empl.setWorkerId(employeeCode);
				empl.setJoinTime(new Date());
				//添加运维人员的同时更新与咖啡机关联表信息
				employeeService.insertEmployee(empl, maps);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResultBean.error();
			}
		}else {
			return ResultBean.error(MxsConstants.CODE1, "phoneNumber is error");
		}
		return ResultBean.ok();
	}
	
	@GetMapping("/queryMachine")
	public ResultBean queryMachine4Employee(String employeeCode) {
		List<CoffeeMachine> ms = coffeeMachineService.machineForEmployee(employeeCode);
		if(null == ms) {
			return ResultBean.error();
		}
		return ResultBean.ok(ms);
	}
	
	
	/**
	 * 用户更新登陆密码
	 * @param oldPwd 原始乎密码
	 * @param newPwd 新密码
	 * @return
	 */
	@PostMapping("/updatePwd")
	public ResultBean updatePassword(String phone, String newPwd) {
		if(StringUtils.isNull(phone) || StringUtils.isNull(newPwd)) {
			return ResultBean.error(MxsConstants.CODE1, "用户名或者新密码为空字符串");
		}
		Employee em = employeeService.queryEmployeeByPhone(phone);
		if(null == em) 
			return ResultBean.error(MxsConstants.CODE1, phone + "用户不存在");
		em.setPassword(MD5.md5(newPwd));
		em = employeeService.updateEmployee(em);
		return ResultBean.ok(em);
	}
	/**
	 * 根据运营人员的Id删除
	 * @param workerId
	 * @return
	 */
	@GetMapping("/deleteUser")
	public ResultBean removeUser(String workerId) {
		employeeService.removeEmployee(workerId);
		return ResultBean.ok();
	}
	/**
	 * 分页查询所有的运营人员信息
	 * @return
	 */
	@GetMapping("/getAll")
	public ResultBean getAllEmployee(WorkerVo evo) {
		Integer page = evo.getPage();
		Integer size = evo.getSize();
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<Employee> list = employeeService.getAllEmployeeCriteria(evo);
		Long sum = employeeService.getAllEmployeeNumber(evo);
		if(null == list) {
			return  ResultBean.error();
		}
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 * 批量删除
	 * @param evo
	 * @return
	 */
	@GetMapping("/deleteWorker")
	public ResultBean removeEmployee(List<String> workerIds) {
		boolean flag = employeeService.removeEmployee(workerIds);
		if(flag)
			return ResultBean.ok();
		return ResultBean.error();
			
	}
	/**
	 * 查询运维人员的反馈信息
	 * @param evo
	 * @return
	 */
	
	@GetMapping("/getFeedback")
	public ResultBean getFeedback(WorkerFeedbackVo evo) {
		Integer page = evo.getPage();
		Integer size = evo.getSize();
		if(null == page || null == size) { 
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		List<WorkerFeedback> list = workerFeedbackService.findAll(evo);
		Long sum = workerFeedbackService.queryFeedBackSum(evo);
		if(null == list) {
			return  ResultBean.error();
		}
		return ResultBean.ok(size, page, sum, list);
	}
	
	/**
	 * 运维人员数据导出
	 * @param evo
	 * @return
	 */
	@GetMapping("/exportExcelForWorker")
	public ResultBean exportExcelForWorker(WorkerVo evo) {
		Integer page = evo.getPage();
		Integer size = evo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "EmployeeInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<ExportEmployee> resultList = new ArrayList<>();
		maps.put("workerId", "运维人员Id");
		maps.put("maintainerNumber", "运维人员编号");
		maps.put("nickname", "昵称");
		maps.put("realname", "真是姓名");
		maps.put("phoneNumber", "电话");
		maps.put("password", "密码");
		maps.put("joinTime", "加入时间");
		maps.put("machines", "负责的咖啡机");
		maps.put("company", "公司名称");
		maps.put("photo", "头像地址");
		
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = employeeService.getAllEmployeeNumber(evo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryEmployee(evo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				evo.setPage(i);
				flag = queryEmployee(evo, resultList);
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
	
	private boolean queryEmployee(WorkerVo evo, List<ExportEmployee> resultList){
		boolean flag = true;
		List<Employee> result = employeeService.getAllEmployeeCriteria(evo);
		if(null == result) {
			return false;
		}
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Employee em = result.get(i);
			String workerId = em.getWorkerId();
			ExportEmployee eem = new ExportEmployee();
			resultList.add(eem);
			Set<String> set = new HashSet<>();
			eem.setMachines(set);
			BeanUtils.copyProperties(em, eem);
			List<EmployeeMachines> list = employeeMachinesService.getMachinesByWorker(workerId);
			for (EmployeeMachines employeeMachines : list) {
				set.add(employeeMachines.getMachineId());
			}
		}
		return flag;
	}
	
	/**
	 * 运维人员反馈信息导出
	 * @param evo
	 * @return
	 */
	@GetMapping("/exportExcelForFeedback")
	public ResultBean exportExcel4Feedback(WorkerFeedbackVo evo) {
		Integer page = evo.getPage();
		Integer size = evo.getSize();
		boolean flag = true;
		String filePath = application.getExcelPath() + "EmployeeInfo.xlsx";
		File file = new File(filePath);
		//1 导出表格的表头和每列数据与model中的对应数据
		Map<String, String> maps = new HashMap<>();
		List<WorkerFeedback> resultList = new ArrayList<>();
		maps.put("id", "运维人员反馈Id");
		maps.put("workerId", "运维人员Id");
		maps.put("createTime", "反馈时间");
		maps.put("description", "反馈内容");
		maps.put("type", "反馈类型");
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		Long sum = workerFeedbackService.queryFeedBackSum(evo);
		//2 查询需要导出的数据
		//sum总数与size进行比较
		if(sum <= size) {
			flag = queryFeedback(evo, resultList);
			if(!flag) {
				log.info("查询数据失败");
				return ResultBean.error(MxsConstants.CODE1, MxsConstants.ERROR);
			}
		}else{
			int pages = sum % size == 0 ? (int)(sum/size) : (int)(sum/size) + 1;
			//循环将所有数据写入，循环中将查询参数的page的数值递增
			for (int i = 0; i < pages; i++) {
				evo.setPage(i);
				flag = queryFeedback(evo, resultList);
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
	
	private boolean queryFeedback(WorkerFeedbackVo evo, List<WorkerFeedback> resultList){
		boolean flag = true;
		List<WorkerFeedback> result = workerFeedbackService.findAll(evo);
		if(null == result) {
			return false;
		}
		resultList.addAll(result);
		return flag;
	}
}
