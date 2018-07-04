package com.mixiusi.vo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author liukun
 *
 */
public class UseVo {
	private Integer id;	
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email
	private String email;
	@NotNull
	private Boolean sex;
	@NotNull
	private Boolean isAdmin;
	private Date createDate;
	
	private List<Integer> pids;

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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

	public List<Integer> getPids() {
		return pids;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setPids(List<Integer> pids) {
		this.pids = pids;
	}

	@Override
	public String toString() {
		return "UseVo [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", sex="
				+ sex + ", isAdmin=" + isAdmin + ", createDate=" + createDate + ", pids=" + pids + "]";
	}
	
}
