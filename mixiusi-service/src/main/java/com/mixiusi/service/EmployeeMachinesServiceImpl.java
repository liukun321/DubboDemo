package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.EmployeeMachines;
import com.mixiusi.biz.EmployeeMachinesBiz;

@Service(interfaceClass = EmployeeMachinesService.class)
public class EmployeeMachinesServiceImpl implements EmployeeMachinesService {

	@Autowired
	private EmployeeMachinesBiz employeeMachinesBiz;
	
	@Override
	public List<EmployeeMachines> getMachinesByWorker(String workerId) {
		return employeeMachinesBiz.getMachinesByWorker(workerId);
	}

	@Override
	public EmployeeMachines getMachineById(String machineId) {
		return employeeMachinesBiz.getMachineById(machineId);
	}

}
