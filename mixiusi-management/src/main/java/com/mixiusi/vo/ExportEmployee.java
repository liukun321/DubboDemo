package com.mixiusi.vo;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExportEmployee {
	/**
	 * 运维人员编号
	 */
	private String maintainerNumber;
	private String workerId;
	/**
	 * 昵称
	 */
	private String nickname;
	//真实姓名
	private String realname;
	//电话
	private String phoneNumber;
	//密码
	private String password;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
	/**
	 * 在创建运维人员时，必须分配责任内的咖啡机
	 * 是关联咖啡机的编号还是咖啡机的所有信息？？？？？
	 */
	private Set<String> machines;
	//公司名称
	private String company = "杭州米修斯科技有限公司";
	//头像图片地址
	private String photo;
	public String getWorkerId() {
		return workerId;
	}
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
	public String getCompany() {
		return company;
	}
	public String getPhoto() {
		return photo;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
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
	public void setCompany(String company) {
		this.company = company;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Set<String> getMachines() {
		return machines;
	}
	public void setMachines(Set<String> machines) {
		this.machines = machines;
	}
	
	public String getMaintainerNumber() {
		return maintainerNumber;
	}
	public void setMaintainerNumber(String maintainerNumber) {
		this.maintainerNumber = maintainerNumber;
	}
	@Override
	public String toString() {
		return "ExportEmployee [maintainerNumber=" + maintainerNumber + ", workerId=" + workerId + ", nickname="
				+ nickname + ", realname=" + realname + ", phoneNumber=" + phoneNumber + ", password=" + password
				+ ", joinTime=" + joinTime + ", machines=" + machines + ", company=" + company + ", photo=" + photo
				+ "]";
	}
	
}
