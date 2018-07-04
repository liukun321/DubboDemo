package com.mixiusi.vo;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeVo {
	/**
	 * 昵称
	 */
	@NotEmpty
	private String nickname;
	//真实姓名
	@NotEmpty
	private String realname;
	//电话
	@NotEmpty
	private String phoneNumber;
	//密码
	@NotEmpty
	private String password;
	@NotEmpty
	private Date joinTime;
	/**
	 * 在创建运维人员时，必须分配责任内的咖啡机
	 * 是关联咖啡机的编号还是咖啡机的所有信息？？？？？
	 */
	private Set<String> venderNames;
	//公司名称
	private String company = "杭州米修斯科技有限公司";
	public String getNickname() {
		return nickname;
	}
	public String getRealname() {
		return realname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public Set<String> getVenderNames() {
		return venderNames;
	}
	public String getCompany() {
		return company;
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
	public void setPassword(String password) {
		this.password = password;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public void setVenderNames(Set<String> venderNames) {
		this.venderNames = venderNames;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "EmployeeVo [nickname=" + nickname + ", realname=" + realname + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", joinTime=" + joinTime + ", venderNames=" + venderNames + ", company="
				+ company + "]";
	}	
	
	
	
}
