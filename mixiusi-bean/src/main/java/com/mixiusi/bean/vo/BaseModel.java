package com.mixiusi.bean.vo;

import java.io.Serializable;

public class BaseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5573968052098215685L;
	private Integer page;
	private Integer size;
	private String sort;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getSort() {
		return sort;
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
	

}
