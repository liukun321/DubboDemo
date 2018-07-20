package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

public class PickupVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9046350706624326633L;
	private String indentId;
	private String openId;
	private String pickupCode;
	private Date startTime;
	private Date endTime;
	public String getIndentId() {
		return indentId;
	}
	public String getOpenId() {
		return openId;
	}
	public String getPickupCode() {
		return pickupCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
