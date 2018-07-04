package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.vo.CmachineVo;
import com.mixiusi.bean.vo.MachineBaseInfoVo;
import com.mixiusi.bean.vo.MachineStatusVo;

public interface CoffeeMachineService {
	/**
	 * 根据咖啡机编号查询咖啡机
	 * @param machineCode
	 * @return
	 */
	public CoffeeMachine getCoffeeMachineInfo(String machineCode);
	
	/**
	 * 根据员工编号查询职责内的所有咖啡机
	 * @param employeeCode
	 * @return
	 */
	public List<CoffeeMachine> machineForEmployee(String employeeCode);
	/**
	 * 查询所有咖啡机的信息
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<CoffeeMachine> getAllCoffeeMachine(Integer page, Integer size);
	
	/**
	 * 查询所有没有运维人员的咖啡机的信息
	 * @return
	 */
	public List<CoffeeMachine> getAllNoEmployee();
	/**
	 * 添加咖啡机
	 * @param coffeeMachine
	 * @return
	 */
	public CoffeeMachine addCoffeeMachine(CoffeeMachine coffeeMachine);
	/**
	 * 批量查询咖啡机
	 * @param machineCodes
	 * @return
	 */
	public List<CoffeeMachine> queryMachinesByCode(List<String> machineCodes);
	
	/**
	 * 批量保存
	 * @param list
	 */
	public void addCoffeeMachines(List<CoffeeMachine> list);
	
	/**
	 * 批量删除
	 * @param machineCodes
	 * @return
	 */
	
	boolean removeCoffeeMachines(List<String> machineCodes);
	//分页条件查询
	public Page<CoffeeMachine> getAllCoffeeMachineCriteria(CmachineVo mvo);
	
	/**
	 * 条件查询总记录数
	 * @param mvo
	 * @return
	 */
	public Long getCoffeeMachineNumber(CmachineVo mvo);
	
	/**
	 * 咖啡机基本信息分页条件查询
	 * @param baseInfo
	 * @return
	 */
	public Page<CoffeeMachine> queryMachineBaseInfo(MachineBaseInfoVo baseInfo);
	/**
	 * 咖啡机基本信息数量查询
	 * @param baseInfo
	 * @return
	 */
	public Long queryMachineBaseInfoSum(MachineBaseInfoVo baseInfo);
	
	/**
	 * 咖啡机状态信息分页条件查询
	 * @param statusInfo
	 * @return
	 */
	public Page<CoffeeMachine> queryMachineStatus(MachineStatusVo statusInfo);
	public Long queryMachineStatusSum(MachineStatusVo statusInfo);	
	
	
}
