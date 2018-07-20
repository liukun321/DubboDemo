package com.mixiusi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.AppVersion;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.CoffeeMachineAddress;
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.ConnectCenter;
import com.mixiusi.bean.Employee;
import com.mixiusi.bean.EmployeeMachines;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.LoginToken;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.WorkerFeedback;
import com.mixiusi.bean.utils.DateUtils;
import com.mixiusi.bean.utils.MD5;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.service.AppVersionService;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.CoffeeMaterialService;
import com.mixiusi.service.ConnectCenterService;
import com.mixiusi.service.EmployeeMachinesService;
import com.mixiusi.service.EmployeeService;
import com.mixiusi.service.ErrorRecordService;
import com.mixiusi.service.FeedbackService;
import com.mixiusi.service.LoginTokenService;
import com.mixiusi.service.MachineAddressService;
import com.mixiusi.vo.result.ConnectVo;
import com.mixiusi.vo.result.DataInfo;
import com.mixiusi.vo.result.InfoData;
import com.mixiusi.vo.result.LoginVo;
import com.mixiusi.vo.result.MachineDanger;
import com.mixiusi.vo.result.MachineDangerVo;
import com.mixiusi.vo.result.MachineInfoList;
import com.mixiusi.vo.result.MachineInfoVo;
import com.mixiusi.vo.result.MachineListVo;
import com.mixiusi.vo.result.MachineVo;
import com.mixiusi.vo.result.MachineWaringVo;
import com.mixiusi.vo.result.MachineWarningList;
import com.mixiusi.vo.result.MainPage;
import com.mixiusi.vo.result.MainPageVo;
import com.mixiusi.vo.result.UpdateVersionVo;
@RestController
@RequestMapping("/coffee/worker")
public class EmployeeController {
	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	@Reference
	private EmployeeService employeeService;
	@Reference
	private EmployeeMachinesService employeeMachinesService;
	@Reference
	private CoffeeMachineService coffeeMachineService;
	@Reference
	private FeedbackService feedbackService;
	@Reference
	private ConnectCenterService connectCenterService;
	@Reference
	private CoffeeMaterialService coffeeMaterialService;
	@Reference
	private MachineAddressService machineAddressService;
	@Reference
	private ErrorRecordService errorRecordService;
	@Reference
	private AppVersionService appVersionService;
	@Reference
	private LoginTokenService loginTokenService;
	
	/**
	 * 运营人员登陆
	 * @param phone 登陆的用户名，即电话，在后端分配
	 * @param password 登陆密码，初始密码是123456
	 * @return
	 */
	@PostMapping("/user/login")
	public ResultBean login(HttpServletRequest request) {//
//		, @RequestParam(required = true) String phone,@RequestParam(required = true)String password
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		log.info("----------登陆请求成功-----------");
		if(StringUtils.isNull(phone) || StringUtils.isNull(password)) {
			return ResultBean.error(MxsConstants.CODE0, "电话或者密码为空");
		}
		String md5pass = MD5.md5(password);
		Employee em = employeeService.login4Employee(phone, md5pass);
		if(null == em)
			return ResultBean.error(MxsConstants.CODE0, "登陆失败，请确认用户名和密码是否正确");
		LoginVo login = new LoginVo();
		BeanUtils.copyProperties(em, login);
		String token = MD5.md5(phone + DateUtils.dateFormat("yyyyMMddHHmmss"));
		login.setToken(token);
		HttpSession session = request.getSession(true);
		String sessionId = session.getId();
		log.info("当前会话的Id = " + sessionId);
		login.setSessionId(sessionId);
//		session.setAttribute("tokens", token);
//		session.setAttribute("phone", phone);
		//这次登陆，则将上一次登陆的Token设置为失效
//		loginTokenService.setAllByStatus();
		String workerId = employeeService.queryEmployeeByPhone(phone).getWorkerId();
		loginTokenService.addLoginToken(new LoginToken(token, DateUtils.getNextWeek(), workerId, false));
		log.info(login.toString() + "---" + session.getAttribute("tokens") + "--" + session.getAttribute("phone"));
		return ResultBean.ok(login);
	}
	/**
	 * 运维人员主页
	 * @param request
	 * @return
	 */
	@PostMapping("/work/map")
	public ResultBean userMainpage(HttpServletRequest request) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MainPageVo> result = new ArrayList<>();//返回的结果集
		MainPage resultList = new MainPage();
		String workerId = request.getParameter("workerId");
		try {
			//查询运营人员负责的咖啡机
			List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
			log.info("咖啡机数量" + list.size());
			for (CoffeeMachine coffeeMachine : list) {
				MainPageVo main = new MainPageVo();
				main.setMachineId(coffeeMachine.getMachineId());
				result.add(main);
				main.setIs_running(coffeeMachine.getIs_running());
				//查询当前咖啡机所有缺料的料盒
				List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByStatus(coffeeMachine.getMachineId(), 1);
				log.info("料盒数量" + ms.size() + "---" + ms.toString());
				if (ms.isEmpty()) {
					main.setDanger(0);
				} else {
					main.setDanger(1);
					List<DataInfo> map = new ArrayList<>();
					//将所有的缺料料盒信息放进map中
					for (CoffeeMaterial cm : ms) {
						DataInfo di = new DataInfo();
						di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
						map.add(di);
					}
					main.setTypeList(map);
				}
				//获取咖啡机的地址位置
				CoffeeMachineAddress addr = machineAddressService.queryMachineAddressById(coffeeMachine.getMachineId());
				main.setLatitude(addr.getLatitude());
				main.setLongitude(addr.getLongitude());
				//确定咖啡机报警的时间
				List<ErrorRecord> errs = errorRecordService.queryErrorRecord(coffeeMachine.getMachineId());
				if(!errs.isEmpty()) {
					String time = sdfmat.format(errs.get(0).getStartTime());
					main.setTime(time);
				}
			} 
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	
	
	
	/**
	 * 用户更新登陆密码
	 * @param oldPwd 原始乎密码
	 * @param newPwd 新密码
	 * @return
	 */
	@PostMapping("/user/pwd/change")
	public ResultBean userUpdatePassword(HttpServletRequest request) {
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		log.info("---------修改密码请求成功-----------");
		if(StringUtils.isNull(oldPwd) || StringUtils.isNull(newPwd)) {
			return ResultBean.error(MxsConstants.CODE1, "新旧密码为空");
		}
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		Employee em = employeeService.queryEmployeeByPhone(phone);
		if(null == em) 
			return ResultBean.error(MxsConstants.CODE, phone + "登陆失效，请重新登陆");
		String pwd = em.getPassword();
		if(pwd.equals(MD5.md5(oldPwd))) {
			em.setPassword(MD5.md5(newPwd));
			employeeService.updateEmployee(em);
		}else {
			return ResultBean.error(MxsConstants.CODE0, phone + "原始密码不正确，请重新输入");
		}
		return ResultBean.ok();
	}
	
	/**
	 * 咖啡机详情
	 * @return
	 */
	@PostMapping("/work/machine/infos")
	public ResultBean userInfo(HttpServletRequest request) {
		MachineInfoList resultList = new MachineInfoList();
		List<MachineInfoVo> result = new ArrayList<>();
		try {
			String workerId = request.getParameter("workerId");
			String machineId = request.getParameter("keyword");
			if(StringUtils.isNull(machineId)) {
				List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
				log.info("咖啡机数量" + list.size());
				for (CoffeeMachine coffeeMachine : list) {
					MachineDetail(result, coffeeMachine);
				} 
			}else {
				CoffeeMachine cm = coffeeMachineService.getCoffeeMachineById(machineId);
				MachineDetail(result, cm);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	/**
	 * 获取指定咖啡机的详情
	 * @param result
	 * @param coffeeMachine
	 */
	private void MachineDetail(List<MachineInfoVo> result, CoffeeMachine coffeeMachine) {
		MachineInfoVo main = new MachineInfoVo();
		main.setMachineId(coffeeMachine.getMachineId());
		result.add(main);
		main.setMachineVersion(coffeeMachine.getType());
		main.setIs_running(coffeeMachine.getIs_running());
		//查询当前咖啡机所有料盒
		List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByMachineId(coffeeMachine.getMachineId());
		if(ms != null && !ms.isEmpty()) {
			List<InfoData> map = new ArrayList<>();
			//将所有的缺料料盒信息放进map中
			for (CoffeeMaterial cm : ms) {
				InfoData di = new InfoData();
				di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
				di.setDanger(cm.getStatus());
				map.add(di);
			}
			main.setList(map);
		}
	}
	
	@PostMapping("/work/machine/warning")
	public ResultBean userMachineWaning(HttpServletRequest request) {
		MachineWarningList resultList = new MachineWarningList();
		List<MachineWaringVo> result = new ArrayList<>();
		try {
			String machineId = request.getParameter("keyword");
			String workerId = request.getParameter("workerId");
			if (StringUtils.isNull(machineId)) {
				List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
				log.info("咖啡机数量" + list.size());
				for (CoffeeMachine coffeeMachine : list) {
					MachineWarningDetail(result, coffeeMachine);
				}
			} else {
				CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineId);
				MachineWarningDetail(result, cm);
			} 
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	
	/**
	 * 获取指定咖啡机的详情
	 * @param result
	 * @param coffeeMachine
	 */
	private void MachineWarningDetail(List<MachineWaringVo> result, CoffeeMachine coffeeMachine) {
		MachineWaringVo main = new MachineWaringVo();
		main.setMachineId(coffeeMachine.getMachineId());
		result.add(main);
		//查询当前咖啡机所有料盒
		List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByStatus(coffeeMachine.getMachineId(), 1);
		if(ms != null && !ms.isEmpty()) {
			log.info("料盒数量" + ms.size() + "---" + ms.toString());
			List<DataInfo> map = new ArrayList<>();
			main.setDanger(1);
			//将所有的缺料料盒信息放进map中
			for (CoffeeMaterial cm : ms) {
				DataInfo di = new DataInfo();
				di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
				map.add(di);
			}
			main.setTypeList(map);
		}else {
			main.setDanger(0);
		}
	}
	
	@PostMapping("/work/machine/danger")
	public ResultBean userMachineDanger(HttpServletRequest request) {
		MachineDanger md = new MachineDanger();
		List<MachineDangerVo> result = new ArrayList<>();
		try {
			String machineId = request.getParameter("keyword");
			String workerId = request.getParameter("workerId");
			if (StringUtils.isNull(machineId)) {
				List<CoffeeMachine> list = coffeeMachineService.machineForEmployee(workerId);
				log.info("咖啡机数量" + list.size());
				for (CoffeeMachine coffeeMachine : list) {
					MachineDangerDetail(result, coffeeMachine);
				}
			} else {
				CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineId);
				MachineDangerDetail(result, cm);
			} 
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		md.setList(result);
		return ResultBean.ok(md);
	}
	
	/**
	 * 获取当月的缺料记录
	 * @param result
	 * @param coffeeMachine
	 */
	private void MachineDangerDetail(List<MachineDangerVo> result, CoffeeMachine coffeeMachine) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MachineDangerVo main = new MachineDangerVo();
		main.setMachineId(coffeeMachine.getMachineId());
		result.add(main);
		//查询当前咖啡机所有料盒
		List<CoffeeMaterial> ms = coffeeMaterialService.queryMaterialByStatusAndTime(coffeeMachine.getMachineId(), 1);
		log.info("料盒数量" + ms.size() + "---" + ms.toString());
		List<DataInfo> map = new ArrayList<>();
		//将所有的缺料料盒信息放进map中
		for (CoffeeMaterial cm : ms) {
			DataInfo di = new DataInfo();
			di.setType(cm.getCategory() + "(" +cm.getStackNumber() + ")");
			map.add(di);
		}
		main.setTypeList(map);
		main.setStartTime(sdf.format(DateUtils.getFirstDayOfMonth()));
		main.setEndTime(sdf.format(new Date()));
	}
	
	
	/**
	 * 获取运营人员负责的咖啡机列表
	 * @param keyword
	 * @return
	 */
	@PostMapping("/work/machine/list")
	public ResultBean userMachineList(HttpServletRequest request) {//keyword  具体含义      @RequestParam(required = false)String keyword
//		Map<String, Integer> machines = null;
		MachineListVo resultList = new MachineListVo();
		List<MachineVo> result = new ArrayList<>();
		List<EmployeeMachines> employees = new ArrayList<>();
		try {
			String machineId = request.getParameter("keyword");
			String workerId = request.getParameter("workerId");
			employees = employeeMachinesService.getMachinesByWorker(workerId);
			int size = employees.size();
//			machines = em.getMachines();
			if (StringUtils.isNull(machineId)) {
				//将map转换为APP端需要的数据格式 
				for (int i = 0; i < size; i++) {
					MachineVo  mv = new MachineVo();
					result.add(mv);
					mv.setMachineId(employees.get(i).getMachineId());
					mv.setMachineVersion(employees.get(i).getType());
				}
			} else {
				EmployeeMachines machine = employeeMachinesService.getMachineById(machineId);
				MachineVo  mv = new MachineVo();
				result.add(mv);
				mv.setMachineId(machineId);
				mv.setMachineVersion(machine.getType());
			} 
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
		
		resultList.setList(result);
		return ResultBean.ok(resultList);
	}
	/**
	 * 运营人员反馈
	 * @param request
	 * @return
	 */
	@PostMapping("/work/feedback")
	public ResultBean userFeedback(HttpServletRequest request) {//keyword  具体含义
		HttpSession session = request.getSession();
		String message = request.getParameter("message");
		Integer type = Integer.parseInt(request.getParameter("type"));
		if(StringUtils.isNull(message) || StringUtils.isNull(type))
			return ResultBean.error(MxsConstants.CODE1, "类型或者反馈信息为空");
		String phone = (String) session.getAttribute("phone");
		WorkerFeedback back = new WorkerFeedback();
		back.setCreateTime(new Date());
		back.setWorkerId(phone);
		back.setDescription(message);
		back.setType(type);
		back = feedbackService.addFeedback(back);
		if(null == back)
			return ResultBean.error(MxsConstants.CODE1, "反馈保存失败");
		return ResultBean.ok();
	}
	/**
	 * 获取联系方式
	 * @param request
	 * @return
	 */
	@PostMapping("/work/call")
	public ResultBean userCallList(HttpServletRequest request) {
		ConnectVo cvo = new ConnectVo();
		List<ConnectCenter> list = connectCenterService.queryAllConnection();
		if(null == list || list.isEmpty())
			return ResultBean.error();
		cvo.setList(list);
		return ResultBean.ok(cvo);
	}
	
	@PostMapping("/system/update")
	public ResultBean userUpdateSystem(HttpServletRequest request) {
		String version = request.getParameter("version");
		log.info("当前的APP版本是===" + version );
		AppVersion app = appVersionService.queryAppVersion();
		UpdateVersionVo uv = new UpdateVersionVo();
		if(null != app && !app.getVersion().equals(version)) {
			log.info("数据库中最新的APP版本是===" + app.getVersion());
			BeanUtils.copyProperties(app, uv);
			uv.setUpdateType(2);
		}else {
			uv.setUpdateType(0);
		}
		return ResultBean.ok(uv);
	}
	
}
