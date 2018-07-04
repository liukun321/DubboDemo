package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class Pickup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6427132645647541138L;

	/**
	 * 订单Id
	 */
	@Id
	private String indentId;
	
	/**
	 * 相应的用户openId
	 */
	@NotNull
	private String openId;
	/**
	 * 取货码
	 */
	@NotNull
	private String pickupCode;
	/**
	 * 生成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	private Boolean isUse;
	
	public Boolean getIsUse() {
		return isUse;
	}
	public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}
	public String getIndentId() {
		return indentId;
	}
	public String getOpenId() {
		return openId;
	}
	public String getPickupCode() {
		return pickupCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((indentId == null) ? 0 : indentId.hashCode());
		result = prime * result + ((isUse == null) ? 0 : isUse.hashCode());
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
		result = prime * result + ((pickupCode == null) ? 0 : pickupCode.hashCode());
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
		Pickup other = (Pickup) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (indentId == null) {
			if (other.indentId != null)
				return false;
		} else if (!indentId.equals(other.indentId))
			return false;
		if (isUse == null) {
			if (other.isUse != null)
				return false;
		} else if (!isUse.equals(other.isUse))
			return false;
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		if (pickupCode == null) {
			if (other.pickupCode != null)
				return false;
		} else if (!pickupCode.equals(other.pickupCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pickup [indentId=" + indentId + ", openId=" + openId + ", pickupCode=" + pickupCode + ", createTime="
				+ createTime + ", isUse=" + isUse + "]";
	}
	
}
