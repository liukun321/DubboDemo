package com.mixiusi.biz;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mixiusi.repository.read.EmployeeMachinesReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.EmployeeMachines;
import com.mixiusi.repository.write.EmployeeMachinesRepository;

@Service
public class EmployeeMachinesBiz {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private EmployeeMachinesRepository employeeMachinesRepository;
	@Autowired
	private EmployeeMachinesReadRepository employeeMachinesReadRepository;
	/**
	 * 根据运维人员Id查询负责的咖啡机
	 * @param workerId
	 * @return
	 */
	public List<EmployeeMachines> getMachinesByWorker(String workerId){
		return employeeMachinesReadRepository.findByWorkerId(workerId);
	}
	
	
	public EmployeeMachines getMachineById(String machineId) {
		return employeeMachinesReadRepository.findByMachineId(machineId);
	}
	
	public void batchInsert(List<EmployeeMachines> machines) {
		for (int i = 0; i < machines.size(); i++) {
			entityManager.persist(machines.get(i));
		}
		entityManager.flush();
		entityManager.clear();
	}
	
	public void batchRemove(String workerId) {
		List<EmployeeMachines> machines = getMachinesByWorker(workerId);
		if(null != machines && !machines.isEmpty()) {
			for (int i = 0; i < machines.size(); i++) {
				entityManager.remove(machines.get(i));
			}
			entityManager.flush();
			entityManager.clear();
		}
	}
}
