package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class ErrorRecordVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8361610350681170804L;
	private String machineId;
	/**
	 * 错误开始时间
	 */
	private Date startTime;
	/**
	 * 错误结束时间
	 */
	private Date endTime;
	/**
	 * 错误持续时长/分
	 */
	private Long durationTime;
	
	/**
	 * 错误类型
	 * 0 缺料预警
	 * 1  严重警告
	 * 2 停机
	 */
	private Integer type;
	//运营人员编号
	private String workerId;
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
	public Integer getType() {
		return type;
	}
	public String getWorkerId() {
		return workerId;
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
	public void setType(Integer type) {
		this.type = type;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
}
