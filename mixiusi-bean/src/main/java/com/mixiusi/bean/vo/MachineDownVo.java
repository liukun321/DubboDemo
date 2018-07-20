package com.mixiusi.bean.vo;

import java.io.Serializable;

public class MachineDownVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6111563225701538511L;
	private String machineId;
	private String employeeCode;
	private Integer type;
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	private String realName;

	public String getMachineId() {
		return machineId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public String getRealName() {
		return realName;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
