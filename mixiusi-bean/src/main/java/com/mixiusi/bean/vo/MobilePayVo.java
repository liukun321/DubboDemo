package com.mixiusi.bean.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 小程序端订单管理
 * @author liukun
 *
 */
public class MobilePayVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8817798063764581692L;
	private String indentId;
	/**
	 * 取货咖啡机ID
	 * 在生成订单后可以为空，在取货后才能进行赋值
	 */
//	private String machineId;
	
	/**
	 * 用户的唯一表示openId
	 */
	private String openId;
	private Date startTime;
	private Date endTime;
//	private Date finishTime;
	public String getIndentId() {
		return indentId;
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
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
//	private Double totalPrice;
	
	/**
	 * 订单中咖啡的杯数
	 */
//	private Integer totalCup;
	/**
	 * 是否取货
	 * 默认未取货
	 */
//	private Boolean over;
	
}
