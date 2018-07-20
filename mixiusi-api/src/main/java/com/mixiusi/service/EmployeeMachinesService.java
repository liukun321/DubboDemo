package com.mixiusi.service;

import java.util.List;

import com.mixiusi.bean.EmployeeMachines;

public interface EmployeeMachinesService {
	/**
	 * 根据运维人员Id查询负责的所有咖啡机信息
	 * @param workerId
	 * @return
	 */
	List<EmployeeMachines> getMachinesByWorker(String workerId);
	/**
	 * 根据咖啡机Id查询
	 * @param machineId
	 * @return
	 */
	EmployeeMachines getMachineById(String machineId);
}
