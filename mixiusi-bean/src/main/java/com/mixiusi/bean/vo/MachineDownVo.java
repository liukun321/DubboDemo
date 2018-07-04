package com.mixiusi.bean.vo;

import java.io.Serializable;

public class MachineDownVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
	private String machineId;
	private String employeeCode;
	private Integer type;
	public Integer getType() {
		return type;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public Integer getPage() {
		return page;
	}

	public Integer getSize() {
		return size;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
