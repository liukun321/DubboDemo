package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠券
 * @author liukun
 *
 */
@Entity
public class Coupons implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3268157264501194130L;
	@Id
	private Integer id;
	/**
	 * 优惠券类型
	 * 1： 折扣类型，打8折
	 * 2：优惠具体金额
	 * 3：赠送指定的咖啡
	 */
	private Integer type;
	/**
	 * 优惠具体金额
	 */
	private String value;//优惠金额
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 优惠券编码
	 */
	@NotNull
	private String couponCode;
	/**
	 * 是否使用过
	 */
	private boolean is_use;
	public Integer getId() {
		return id;
	}
	public Integer getType() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public boolean isIs_use() {
		return is_use;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public void setIs_use(boolean is_use) {
		this.is_use = is_use;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couponCode == null) ? 0 : couponCode.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (is_use ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Coupons other = (Coupons) obj;
		if (couponCode == null) {
			if (other.couponCode != null)
				return false;
		} else if (!couponCode.equals(other.couponCode))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_use != other.is_use)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Coupons [id=" + id + ", type=" + type + ", value=" + value + ", endTime=" + endTime + ", couponCode="
				+ couponCode + ", is_use=" + is_use + "]";
	}
	
}
