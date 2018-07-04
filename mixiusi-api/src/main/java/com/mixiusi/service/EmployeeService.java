package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Employee;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.vo.WorkerVo;

public interface EmployeeService {
	Employee insertEmployee(Employee employee);
	//根据电话查询运营人员信息
	Employee queryEmployeeByPhone(String phone);
	//根据电话查询运营人员信息
	Employee queryEmployeeById(String workerId);
	//查询运营人员的错误记录
	List<ErrorRecord> queryWorkerId(String workerId);
	//删除运营人员
	void removeEmployee(String workerId);
	
	Page<Employee> getAllEmployee(Integer page, Integer size);
	
	Page<Employee> getAllEmployeeCriteria(WorkerVo evo);
	
	Long getAllEmployeeNumber(WorkerVo evo);
	
	boolean remiveEmployee(List<String> workerIds);
}
