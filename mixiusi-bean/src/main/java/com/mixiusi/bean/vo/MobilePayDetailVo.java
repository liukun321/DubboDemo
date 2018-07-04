package com.mixiusi.bean.vo;

import java.io.Serializable;

public class MobilePayDetailVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
	private String indentId;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getSort() {
		return sort;
	}
	public String getIndentId() {
		return indentId;
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
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	
}
