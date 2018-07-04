package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

public class PickupVo implements Serializable{
	private Integer page;
	private Integer size;
	private String indentId;
	private String openId;
	private String pickupCode;
	private Date startTime;
	private Date endTime;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
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
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
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
