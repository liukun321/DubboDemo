package com.mixiusi.bean.vo;

import java.io.Serializable;

public class CouponVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9097234990580707104L;
	private String couponCode;
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	
	
}
