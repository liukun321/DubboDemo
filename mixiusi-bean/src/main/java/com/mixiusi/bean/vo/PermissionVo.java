package com.mixiusi.bean.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class PermissionVo implements Serializable{
	private Integer page;
	private Integer size;
	private String name;
    private String description;
    private String url;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
}
