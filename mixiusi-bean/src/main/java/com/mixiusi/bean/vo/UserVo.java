package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

public class UserVo implements Serializable{
	private Integer page;
	private Integer size;
	private String userName;
	private String email;
	private Boolean sex;
	/**
	 * true : 是最高管理员
	 * false: 不是最高管理员
	 */
	private Boolean isAdmin;
    private Date createDate;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getUserName() {
		return userName;
	}
	public String getEmail() {
		return email;
	}
	public Boolean getSex() {
		return sex;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    
}
