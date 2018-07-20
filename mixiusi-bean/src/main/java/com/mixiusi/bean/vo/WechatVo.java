package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

public class WechatVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 894290897603998316L;
	private String indentId;
	private String machineId;
	
	private String openId;
	private Integer credit;
	private Date payTime;
	private Date finishTime;
	
	private Double totalPrice;
	
	private Integer totalCup;
	private Boolean over;
	public String getIndentId() {
		return indentId;
	}
	public String getMachineId() {
		return machineId;
	}
	public String getOpenId() {
		return openId;
	}
	public Integer getCredit() {
		return credit;
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
	public Boolean getOver() {
		return over;
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
	public void setCredit(Integer credit) {
		this.credit = credit;
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
	public void setOver(Boolean over) {
		this.over = over;
	}
	
	
}
