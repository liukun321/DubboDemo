package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

public class UserVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5324370488060887591L;
	private String userName;
	private String email;
	private Boolean sex;
	/**
	 * true : 是最高管理员
	 * false: 不是最高管理员
	 */
	private Boolean isAdmin;
    private Date createDate;
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
