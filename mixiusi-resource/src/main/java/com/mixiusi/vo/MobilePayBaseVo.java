package com.mixiusi.vo;

import java.util.Date;

public class MobilePayBaseVo {
	private String indentId;
	/**
	 * 取货咖啡机ID
	 * 在生成订单后可以为空，在取货后才能进行赋值
	 */
	private String machineId;
	
	/**
	 * 用户的唯一表示openId
	 */
	private String openId;
	/**
	 * 积分
	 */
	private Integer credit;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 订单完成时间，即取货时间
	 */
	private Date finishTime;
	
	/**
	 * 订单总价
	 */
	private Double totalPrice;
	
	/**
	 * 订单中咖啡的杯数
	 */
	private Integer totalCup;

	public String getIndentId() {
		return indentId;
	}

	public String getMachineId() {
		return machineId;
	}

	public String getOpenId() {
		return openId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public Integer getTotalCup() {
		return totalCup;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalCup(Integer totalCup) {
		this.totalCup = totalCup;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	
}
