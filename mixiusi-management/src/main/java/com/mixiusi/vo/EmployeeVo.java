package com.mixiusi.vo;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeVo {
	/**
	 * 昵称
	 */
	private String nickname;
	//真实姓名
	@NotEmpty
	private String realname;
	//电话
	@NotEmpty
	private String phoneNumber;
	/**
	 * 在创建运维人员时，必须分配责任内的咖啡机
	 * 是关联咖啡机的编号还是咖啡机的所有信息？？？？？
	 */
	private Map<String, Integer> machines;
	/**
	 * 运维人员编号
	 */
	@NotEmpty
	private String maintainerNumber;
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
	public void setCompany(String company) {
		this.company = company;
	}
	public Map<String, Integer> getMachines() {
		return machines;
	}
	public String getMaintainerNumber() {
		return maintainerNumber;
	}
	public void setMachines(Map<String, Integer> machines) {
		this.machines = machines;
	}
	public void setMaintainerNumber(String maintainerNumber) {
		this.maintainerNumber = maintainerNumber;
	}
	@Override
	public String toString() {
		return "EmployeeVo [nickname=" + nickname + ", realname=" + realname + ", phoneNumber=" + phoneNumber
				+ ", machines=" + machines + ", maintainerNumber=" + maintainerNumber + ", company=" + company + "]";
	}
	
}
