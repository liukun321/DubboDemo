package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 咖啡机基本信息
 * @author liukun
 *
 */
public class MachineStatusVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1719390806521962078L;
   	private String machineId;
	private String machineCode;
	private Boolean is_running;
	private String employeeCode;
	private String realname;
	public String getMachineId() {
		return machineId;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public Boolean getIs_running() {
		return is_running;
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
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
