package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;


public class WorkerFeedbackVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5641388887143649270L;
	private String workerId;
	private Date startTime;
	private Date endTime;
	
	public String getWorkerId() {
		return workerId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
