package com.mixiusi.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class LoginToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 保存的token值
	 */
	@NotNull
	private String token;
	/**
	 * token的过期时间
	 */
	@NotNull
	private Date expireTime;
	/**
	 * 运维人员的Id
	 */
	@NotNull
	private String workerId;
	/**
	 * 是否过过期
	 */
	@NotNull
	private Boolean failure = false;
	
	
	public LoginToken() {
	}
	
	public LoginToken(String token, Date expireTime, String workerId, Boolean failure) {
		super();
		this.token = token;
		this.expireTime = expireTime;
		this.workerId = workerId;
		this.failure = failure;
	}

	public Integer getId() {
		return id;
	}
	public String getToken() {
		return token;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Boolean getFailure() {
		return failure;
	}

	public void setFailure(Boolean failure) {
		this.failure = failure;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
		result = prime * result + ((failure == null) ? 0 : failure.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		LoginToken other = (LoginToken) obj;
		if (expireTime == null) {
			if (other.expireTime != null)
				return false;
		} else if (!expireTime.equals(other.expireTime))
			return false;
		if (failure == null) {
			if (other.failure != null)
				return false;
		} else if (!failure.equals(other.failure))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
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
		return "LoginToken [id=" + id + ", token=" + token + ", expireTime=" + expireTime + ", workerId=" + workerId
				+ ", failure=" + failure + "]";
	}
	
}
