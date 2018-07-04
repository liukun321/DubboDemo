package com.mixiusi.bean.vo;

import java.io.Serializable;

public class CoffeeVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6528472657401006835L;
	private Integer page;
	private Integer size;
	private String sort;
	private String coffeeId;
	
	public CoffeeVo() {
	}
	public Integer getPage() {
		return page;
	}
	public String getSort() {
		return sort;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	
}
