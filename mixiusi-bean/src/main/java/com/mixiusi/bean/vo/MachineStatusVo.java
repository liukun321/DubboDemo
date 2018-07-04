package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 咖啡机基本信息
 * @author liukun
 *
 */
public class MachineStatusVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
	
   	private String machineId;
	private String machineCode;
	private Boolean is_running;
	private String employeeCode;
	private String realname;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
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
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
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
