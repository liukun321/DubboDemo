package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SaleStatisticVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
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
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getSort() {
		return sort;
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
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
