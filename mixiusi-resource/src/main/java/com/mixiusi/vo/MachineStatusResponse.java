package com.mixiusi.vo;

import java.util.Date;
/**
 * 咖啡机状态查询反馈
 * @author liukun
 *
 */
public class MachineStatusResponse {
	private String machineId;
	private Boolean is_running;
	private Date updateTime;
	private Date repairTime;
	private String employeeCode;
	private String realname;
	private String machineCode;
	
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMachineId() {
		return machineId;
	}
	public Boolean getIs_running() {
		return is_running;
	}
	public Date getRepairTime() {
		return repairTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public String getRealname() {
		return realname;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
