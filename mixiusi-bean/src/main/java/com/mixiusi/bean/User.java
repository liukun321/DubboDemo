package com.mixiusi.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理平台用户表
 * @author liukun
 *
 */
@Entity
@Table(name = "mxs_user")
public class User implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 2238283670998841355L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email
	private String email;
	/**
	 * true : 男
	 * false: 女
	 */
	@NotNull
	private Boolean sex;
	/**
	 * true : 是最高管理员
	 * false: 不是最高管理员
	 */
	@NotNull
	private Boolean isAdmin;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
	public Integer getId() {
		return id;
	}
	public String getUserName() {
		return userName;
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
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", sex="
				+ sex + ", isAdmin=" + isAdmin + ", createDate=" + createDate + "]";
	}
	
}
