package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * 微信用户信息
 * @author liukun
 *
 */
@Entity
@Table(name = "wechat_user")
public class WechatUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3319912806767559407L;
	@Id
	private String openId;
	/**
	 * 用户 积分
	 */
	@NotNull
	private Integer credit;
	public String getOpenId() {
		return openId;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credit == null) ? 0 : credit.hashCode());
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
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
		WechatUser other = (WechatUser) obj;
		if (credit == null) {
			if (other.credit != null)
				return false;
		} else if (!credit.equals(other.credit))
			return false;
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [openId=" + openId + ", credit=" + credit + "]";
	}
	
}
