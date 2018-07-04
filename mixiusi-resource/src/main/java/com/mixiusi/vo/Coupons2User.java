package com.mixiusi.vo;

import java.util.Date;

public class Coupons2User {
	private String openId;
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
	
	private Date endTime;
	/**
	 * 优惠券编码
	 */
	private String couponCode;
	/**
	 * 折扣值
	 */
	private String discount;
	/**
	 * 赠送咖啡的ID
	 */
	private Integer tocoffee;
	/**
	 * 是否使用过
	 */
	private boolean is_use;
	public String getOpenId() {
		return openId;
	}
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
	public String getDiscount() {
		return discount;
	}
	public Integer getTocoffee() {
		return tocoffee;
	}
	public boolean isIs_use() {
		return is_use;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public void setTocoffee(Integer tocoffee) {
		this.tocoffee = tocoffee;
	}
	public void setIs_use(boolean is_use) {
		this.is_use = is_use;
	}
	
	
}
