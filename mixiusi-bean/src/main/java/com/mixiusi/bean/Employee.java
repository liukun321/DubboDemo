package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 运营人员
 * @author liukun
 *
 */
@Entity
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1166620198244266078L;
	/**
	 * 运维人员ID
	 */
	@Id
	private String workerId;
	/**
	 * 运维人员编号
	 */
	private String maintainerNumber;
	/**
	 * 昵称
	 */
	private String nickname;
	//真实姓名
	@NotNull
	private String realname;
	//电话
	@NotNull
	private String phoneNumber;
	//密码
	@NotNull
	private String password;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
	/**
	 * 在创建运维人员时，必须分配责任内的咖啡机
	 * 是关联咖啡机的编号还是咖啡机的所有信息？？？？？
	 */
	//公司名称
	private String company = "杭州米修斯科技有限公司";
	//头像图片地址
	private String photo;
	
	public String getMaintainerNumber() {
		return maintainerNumber;
	}
	public void setMaintainerNumber(String maintainerNumber) {
		this.maintainerNumber = maintainerNumber;
	}
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
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((joinTime == null) ? 0 : joinTime.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((realname == null) ? 0 : realname.hashCode());
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
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
		Employee other = (Employee) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (joinTime == null) {
			if (other.joinTime != null)
				return false;
		} else if (!joinTime.equals(other.joinTime))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (realname == null) {
			if (other.realname != null)
				return false;
		} else if (!realname.equals(other.realname))
			return false;
		if (workerId == null) {
			if (other.workerId != null)
				return false;
		} else if (!workerId.equals(other.workerId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Employee [workerId=" + workerId + ", maintainerNumber=" + maintainerNumber + ", nickname=" + nickname
				+ ", realname=" + realname + ", phoneNumber=" + phoneNumber + ", password=" + password + ", joinTime="
				+ joinTime + ", company=" + company + ", photo=" + photo + "]";
	}
	
	
}
