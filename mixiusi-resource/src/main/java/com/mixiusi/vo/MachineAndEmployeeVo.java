package com.mixiusi.vo;

import java.util.Date;

public class MachineAndEmployeeVo {
	private String machineId;
	/**
	 * 咖啡机类型/0初代机、1二代机
	 */
	private Integer type;
	private Date downTime;
	/**
	 * 咖啡机编号，标明初代机和二代机的编号
	 */
	private String machineCode;
	
	/**
	 * 咖啡机的状态
	 * int SYS_STATUS_INIT = 100;
	 * int SYS_STATUS_DEBUG = 200;
	 * int SYS_STATUS_WAIT = 300;
	 * int SYS_STATUS_WASH = 400;
	 * int SYS_STATUS_LOAD = 500;
	 * int SYS_STATUS_PAY = 600;
	 * int SYS_STATUS_WORK = 700;
	 * 
	 */
	private Integer status;
	/**
	 * 咖啡机的是否正常运行
	 */
	private Boolean is_running;
	/**
	 * 咖啡机设立时间
	 */
	private Date createTime;
	/**
	 * 咖啡机 物料更新时间
	 */
	private Date updateTime;
	/**
	 * 咖啡机修复时间, 即重新正常运行时间
	 */
	private Date repaireTime;
	/**
	 * 咖啡机负责的运维人员编号,在创建时允许为空
	 */
	private String employeeCode;
	
	private String realname;

	public String getMachineId() {
		return machineId;
	}

	public Integer getType() {
		return type;
	}

	public Date getDownTime() {
		return downTime;
	}

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

	public String getRealname() {
		return realname;
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

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
