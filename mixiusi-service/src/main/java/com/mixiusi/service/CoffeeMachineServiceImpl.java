package com.mixiusi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.vo.CmachineVo;
import com.mixiusi.bean.vo.MachineBaseInfoVo;
import com.mixiusi.bean.vo.MachineStatusVo;
import com.mixiusi.biz.CoffeeMachineBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 
 * @author liukun
 *
 */
@Service(interfaceClass = CoffeeMachineService.class)
public class CoffeeMachineServiceImpl implements CoffeeMachineService{
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineServiceImpl.class);
	@Autowired
	private CoffeeMachineBiz coffeeMachineBiz;
	@Reference(version = "1.0.0")
	private SequenceService sequenceService;

	@Override
	public CoffeeMachine getCoffeeMachineInfo(String machineCode) {
		return coffeeMachineBiz.getCoffeeMachineInfo(machineCode);
	}

	@Override
	public List<CoffeeMachine> machineForEmployee(String employeeCode) {
		return coffeeMachineBiz.machineForEmployee(employeeCode);
	}

	@Override
	public CoffeeMachine addCoffeeMachine(CoffeeMachine coffeeMachine) {
		return coffeeMachineBiz.addCoffeeMachine(coffeeMachine);
	}
	@Transactional
	@Override
	public List<CoffeeMachine> queryMachinesByCode(List<String> machineCodes) {
		return coffeeMachineBiz.queryMachinesByCode(machineCodes);
	}
	@Transactional
	@Override
	public void addCoffeeMachines(List<CoffeeMachine> list) {
		coffeeMachineBiz.addCoffeeMachines(list);
	}
	/**
	 * 查找所有没有绑定运营人员的咖啡机
	 */
	@Override
	public List<CoffeeMachine> getAllNoEmployee() {
		return coffeeMachineBiz.getAllNoEmployee();
	}

	@Override
	public Long generateMachineCode() {
		//TODO 使用Redis的单线程特性，实现咖啡机 编号的递增
		return sequenceService.generate("machineCode", 10000);
	}

	@Override
	public CoffeeMachine getCoffeeMachineById(String machineId) {
		return coffeeMachineBiz.getCoffeeMachineById(machineId);
	}

	//----------以上是机器端的接口-------------
	@Override
	public Page<CoffeeMachine> getAllCoffeeMachine(Integer page, Integer size) {
		return coffeeMachineBiz.getAllCoffeeMachine(page, size);
	}

	@Override
	public boolean removeCoffeeMachines(List<String> machineCodes) {
		return coffeeMachineBiz.removeCoffeeMachines(machineCodes);
	}

	@Override
	public List<CoffeeMachine> getAllCoffeeMachineCriteria(CmachineVo mvo) {
		return coffeeMachineBiz.getAllCoffeeMachineCriteria(mvo).getContent();
	}

	@Override
	public Long getCoffeeMachineNumber(CmachineVo mvo) {
		return coffeeMachineBiz.getCoffeeMachineNumber(mvo);
	}

	@Override
	public List<CoffeeMachine> queryMachineBaseInfo(MachineBaseInfoVo baseInfo) {
		return coffeeMachineBiz.queryMachineBaseInfo(baseInfo);
	}

	@Override
	public Long queryMachineBaseInfoSum(MachineBaseInfoVo baseInfo) {
		return coffeeMachineBiz.queryMachineBaseInfoSum(baseInfo);
	}

	@Override
	public List<CoffeeMachine> queryMachineStatus(MachineStatusVo statusInfo) {
		return coffeeMachineBiz.queryMachineStatus(statusInfo).getContent();
	}

	@Override
	public Long queryMachineStatusSum(MachineStatusVo statusInfo) {
		return coffeeMachineBiz.queryMachineStatusSum(statusInfo);
	}
	
}
