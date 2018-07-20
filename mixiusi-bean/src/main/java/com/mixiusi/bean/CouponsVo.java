package com.mixiusi.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CouponsVo {
	/**
	 * 类型：1 折扣
	 * 2 减免
	 * 3赠送
	 */
	@NotEmpty
	private Integer type;
	/**
	 * 优惠总金额
	 */
	private Integer sumMoney;
	/**
	 * 总数量
	 */
	private Integer count;
	/**
	 * 增送咖啡的Id
	 */
	private Map<String, Integer> coffeeIds;
	/**
	 * 打折的力度
	 */
	private List<Double> discounts;
	@NotEmpty
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
//	/**
//	 * 优惠券编码
//	 */
//	private String couponCode;
	public Integer getType() {
		return type;
	}
	public Integer getSumMoney() {
		return sumMoney;
	}
	public Integer getCount() {
		return count;
	}
	public List<Double> getDiscounts() {
		return discounts;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setSumMoney(Integer sumMoney) {
		this.sumMoney = sumMoney;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setDiscounts(List<Double> discounts) {
		this.discounts = discounts;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Map<String, Integer> getCoffeeIds() {
		return coffeeIds;
	}
	public void setCoffeeIds(Map<String, Integer> coffeeIds) {
		this.coffeeIds = coffeeIds;
	}
	@Override
	public String toString() {
		return "CouponsVo [type=" + type + ", sumMoney=" + sumMoney + ", count=" + count + ", coffeeIds=" + coffeeIds
				+ ", discounts=" + discounts + ", endTime=" + endTime + "]";
	}
	
	
}
