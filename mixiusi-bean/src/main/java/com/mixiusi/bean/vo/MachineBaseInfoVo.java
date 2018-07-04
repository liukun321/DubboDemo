package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 咖啡机基本信息
 * @author liukun
 *
 */
public class MachineBaseInfoVo implements Serializable{
	private Integer page;
	private Integer size;
   	private String machineId;
   	private String machineCode;
	private Integer type;
	private Date startTime;
	private Date endTime;
	private String workerId;
	private String realname;
	public String getMachineId() {
		return machineId;
	}
	public Integer getType() {
		return type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
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
	public void setType(Integer type) {
		this.type = type;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setRealname(String realname) {
		this.realname = realname;
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
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	
}
