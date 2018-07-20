package com.mixiusi.service;

import java.util.List;
import java.util.Map;

import com.mixiusi.bean.Employee;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.vo.WorkerVo;
/**
 * 运维人员接口
 * @author liukun
 *
 */
public interface EmployeeService {
	Employee insertEmployee(Employee employee, Map<String, Integer> maps);
	//根据电话查询运营人员信息
	Employee queryEmployeeByPhone(String phone);
	//根据电话查询运营人员信息
	Employee queryEmployeeById(String workerId);
	//查询运营人员的错误记录
	List<ErrorRecord> queryWorkerId(String workerId);
	//删除运营人员
	void removeEmployee(String workerId);
	
	public Employee login4Employee(String tel, String password);
	
	List<Employee> getAllEmployee(Integer page, Integer size);
	
	List<Employee> getAllEmployeeCriteria(WorkerVo evo);
	
	Long getAllEmployeeNumber(WorkerVo evo);
	
	boolean removeEmployee(List<String> workerIds);
	/**
	 * 更新运维人员信息
	 * @param em
	 * @return 
	 */
	Employee updateEmployee(Employee em);
}
