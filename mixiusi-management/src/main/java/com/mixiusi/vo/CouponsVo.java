package com.mixiusi.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CouponsVo {
	@NotEmpty
	private Integer type;
	/**
	 * 优惠具体金额
	 */
	private String value;//优惠金额
	@NotEmpty
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 优惠券编码
	 */
	@NotEmpty
	private String couponCode;
	/**
	 * 折扣值
	 */
	private String discount;
	/**
	 * 赠送咖啡的ID
	 */
	private Integer tocoffee;
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
	public String getDiscount() {
		return discount;
	}
	public Integer getTocoffee() {
		return tocoffee;
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
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public void setTocoffee(Integer tocoffee) {
		this.tocoffee = tocoffee;
	}
	@Override
	public String toString() {
		return "CouponsVo [type=" + type + ", value=" + value + ", endTime=" + endTime + ", couponCode=" + couponCode
				+ ", discount=" + discount + ", tocoffee=" + tocoffee + "]";
	}
	
}
