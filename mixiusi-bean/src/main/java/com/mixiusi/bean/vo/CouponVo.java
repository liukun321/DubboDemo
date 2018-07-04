package com.mixiusi.bean.vo;

import java.io.Serializable;

public class CouponVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
	private String couponCode;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getSort() {
		return sort;
	}
	public String getCouponCode() {
		return couponCode;
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
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	
	
}
