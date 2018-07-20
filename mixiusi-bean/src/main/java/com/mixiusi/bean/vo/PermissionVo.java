package com.mixiusi.bean.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class PermissionVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2576335201365542502L;
	private String name;
    private String description;
    private String url;
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
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
