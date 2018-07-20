package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单查询条件封装
 * @author liukun
 *
 */
public class OrderVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7101069350464900008L;
	private String indentId;
	/**
	 * 咖啡机的Id
	 */
//	private String machineId;
	/**
	 * 订单详情,产品编号
	 */
//	private String coffeeId;
	/**
	 * 支付方式
	 * 0 支付方式错误
	 * 1 支付宝
	 * 2 微信
	 */
	private Integer payMethod;
	/**
	 * 支付状态
	 * 0:待支付
	 * 1：支付成功
	 * 2：支付失败
	 * 3:退款成功
	 * 4：退款失败
	 * 5：订单作废
	 */
	private Integer payStatus;
	private Date startTime;
	private Date endTime;
//	private String orderId;
	/**
	 * 是否热饮
	 */
//	private Boolean isHot;
	/**
	 * 糖度 0 3 5 7 10
	 */
//	private List<Integer> sugar;
	public String getIndentId() {
		return indentId;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
