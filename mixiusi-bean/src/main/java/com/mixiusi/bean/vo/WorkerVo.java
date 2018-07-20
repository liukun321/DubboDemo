package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WorkerVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String workerId;
	/**
	 * 昵称
	 */
	private String nickname;
	//真实姓名
	private String realname;
	//电话
	private String phoneNumber;
	private Date joinTime;
	public String getNickname() {
		return nickname;
	}
	public String getRealname() {
		return realname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	
}
