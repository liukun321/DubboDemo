package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SaleStatisticVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3089081918140354091L;
	private String coffeeId;
	private List<Integer> sugar;
	/**
	 * 
	 *  4      年          
        3   月        
        2    周        
        1     日   
	 */
	private Integer timeType;
	
	private Date startTime;
	private Date endTime;
	
	private Boolean isHot;
	
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public List<Integer> getSugar() {
		return sugar;
	}
	public Integer getTimeType() {
		return timeType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	public void setSugar(List<Integer> sugar) {
		this.sugar = sugar;
	}
	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
