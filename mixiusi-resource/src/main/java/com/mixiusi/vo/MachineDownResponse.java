package com.mixiusi.vo;
/**
 * 咖啡机停机查询反馈
 * @author liukun
 *
 */

import java.util.Date;

public class MachineDownResponse {
	private String machineId;
	private Date startTime;
	private Date endTime;
	private Long durationTime;
	private String workerId;
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
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public Long getDurationTime() {
		return durationTime;
	}
	public String getWorkerId() {
		return workerId;
	}
	public String getRealname() {
		return realname;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
	
}
