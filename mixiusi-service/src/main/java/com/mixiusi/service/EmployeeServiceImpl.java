package com.mixiusi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Employee;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.vo.WorkerVo;
import com.mixiusi.biz.EmployeeBiz;

@Service(interfaceClass = EmployeeService.class, cluster = "failover", loadbalance="roundrobin")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeBiz employeeBiz;
	
	@Override
	public Employee insertEmployee(Employee employee, Map<String, Integer> maps) {
		return employeeBiz.insertEmployee(employee, maps);

	}

	@Override
	public Employee queryEmployeeByPhone(String phone) {
		return employeeBiz.queryEmployeeByPhone(phone);
	}

	@Override
	public Employee login4Employee(String tel, String password) {
		return employeeBiz.login4Employee(tel, password);
	}

	@Override
	public Employee queryEmployeeById(String workerId) {
		return employeeBiz.queryEmployeeById(workerId);
	}

	@Override
	public List<ErrorRecord> queryWorkerId(String workerId) {
		return employeeBiz.queryWorkerId(workerId);
	}

	@Override
	public void removeEmployee(String workerId) {
		employeeBiz.removeEmployee(workerId);
	}

	@Override
	public List<Employee> getAllEmployee(Integer page, Integer size) {
		return employeeBiz.getAllEmployee(page, size).getContent();
	}

	@Override
	public List<Employee> getAllEmployeeCriteria(WorkerVo evo) {
		return employeeBiz.getAllEmployeeCriteria(evo).getContent();
	}

	@Override
	public Long getAllEmployeeNumber(WorkerVo evo) {
		return employeeBiz.getAllEmployeeNumber(evo);
	}

	@Override
	public boolean removeEmployee(List<String> workerIds) {
		return employeeBiz.remiveEmployee(workerIds);
	}

	@Override
	public Employee updateEmployee(Employee em) {
		// TODO Auto-generated method stub
		return null;
	}

}
