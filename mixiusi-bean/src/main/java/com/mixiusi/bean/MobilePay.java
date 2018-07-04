package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 微信订单表
 * @author liukun
 *
 */
@Entity
@Table(name = "wechat_mobilePay")
public class MobilePay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6032199670888720732L;
	/**
	 * 订单Id
	 */
	@Id
	private String indentId;
	/**
	 * 取货咖啡机ID
	 * 在生成订单后可以为空，在取货后才能进行赋值
	 */
	private String machineId;
	
	/**
	 * 用户的唯一表示openId
	 */
	@NotNull
	private String openId;
	/**
	 * 积分
	 */
	@NotNull
	private Integer credit;
	/**
	 * 支付时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;
	/**
	 * 订单完成时间，即取货时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;
	
	/**
	 * 订单总价
	 */
	@NotNull
	private Double totalPrice;
	
	/**
	 * 订单中咖啡的杯数
	 */
	@NotNull
	private Integer totalCup;
	/**
	 * 是否取货
	 * 默认未取货
	 */
	@NotNull
	private Boolean over = false;
//	@NotNull
//	@ElementCollection
//	private List<CoffeeList> coffeeList;
	
//	public List<CoffeeList> getCoffeeList() {
//		return coffeeList;
//	}
//	public void setCoffeeList(List<CoffeeList> coffeeList) {
//		this.coffeeList = coffeeList;
//	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credit == null) ? 0 : credit.hashCode());
		result = prime * result + ((finishTime == null) ? 0 : finishTime.hashCode());
		result = prime * result + ((indentId == null) ? 0 : indentId.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
		result = prime * result + ((over == null) ? 0 : over.hashCode());
		result = prime * result + ((payTime == null) ? 0 : payTime.hashCode());
		result = prime * result + ((totalCup == null) ? 0 : totalCup.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
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
		MobilePay other = (MobilePay) obj;
		if (credit == null) {
			if (other.credit != null)
				return false;
		} else if (!credit.equals(other.credit))
			return false;
		if (finishTime == null) {
			if (other.finishTime != null)
				return false;
		} else if (!finishTime.equals(other.finishTime))
			return false;
		if (indentId == null) {
			if (other.indentId != null)
				return false;
		} else if (!indentId.equals(other.indentId))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		if (over == null) {
			if (other.over != null)
				return false;
		} else if (!over.equals(other.over))
			return false;
		if (payTime == null) {
			if (other.payTime != null)
				return false;
		} else if (!payTime.equals(other.payTime))
			return false;
		if (totalCup == null) {
			if (other.totalCup != null)
				return false;
		} else if (!totalCup.equals(other.totalCup))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MobilePay [indentId=" + indentId + ", machineId=" + machineId + ", openId=" + openId + ", credit="
				+ credit + ", payTime=" + payTime + ", finishTime=" + finishTime + ", totalPrice=" + totalPrice
				+ ", totalCup=" + totalCup + ", over=" + over + "]";
	}

}
