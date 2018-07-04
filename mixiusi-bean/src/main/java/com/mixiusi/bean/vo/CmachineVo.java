package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class CmachineVo implements Serializable{
	private Integer page;
	private Integer size;
	
	private String machineId;
	private Integer type;
	private Date downTime;
	private String machineCode;
	private Integer status;
	/**
	 * 咖啡机的是否正常运行
	 */
	private Boolean is_running;
	private Date createTime;
	private Date updateTime;
	private Date repaireTime;
	/**
	 * 咖啡机负责的运维人员编号
	 */
	private String employeeCode;
	public String getMachineCode() {
		return machineCode;
	}
	public Integer getStatus() {
		return status;
	}
	public Boolean getIs_running() {
		return is_running;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Date getRepaireTime() {
		return repaireTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setRepaireTime(Date repaireTime) {
		this.repaireTime = repaireTime;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getMachineId() {
		return machineId;
	}
	public Integer getType() {
		return type;
	}
	public Date getDownTime() {
		return downTime;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setDownTime(Date downTime) {
		this.downTime = downTime;
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
