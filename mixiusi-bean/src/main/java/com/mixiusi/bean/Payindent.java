package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Payindent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8294147088112637894L;
	/**
	 * 咖啡Id 与咖啡品种关联
	 */
	@Id
	@Column(length = 200)
	private String indentId;
	/**
	 * 咖啡机的Id
	 */
	@Column(nullable = false, length = 100)
	private String machineId;
	/**
	 * 订单详情,产品编号
	 */
	@NotNull
	private String coffeeId;
	@NotNull
	private Double discount_price;
	/**
	 * 订单原价
	 */
	@NotNull
	private Double priceOri;
	/**
	 * 订单价格
	 */
	@NotNull
	private Double price;
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
	@Column(length = 11, nullable = false)
	private Integer payStatus;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 支付订单号，支付宝或者微信
	 * @return
	 */
	private String orderId;
	/**
	 * true 热饮
	 * false 冷饮
	 */
	@NotNull
	private Boolean isHot;
	/**
	 * 糖度
	 * 0 3 5 7 10
	 */
	@NotNull
	private Integer sugar;
	/**
	 * 咖啡浓度
	 * 0 正常
	 * 1 低因
	 * 2 特浓
	 */
	private Integer coffeeType;
	public Boolean getIsHot() {
		return isHot;
	}
	public Integer getCoffeeType() {
		return coffeeType;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public void setCoffeeType(Integer coffeeType) {
		this.coffeeType = coffeeType;
	}
	public String getIndentId() {
		return indentId;
	}
	public String getMachineId() {
		return machineId;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public Double getPriceOri() {
		return priceOri;
	}
	public Double getPrice() {
		return price;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public Integer getSugar() {
		return sugar;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	public void setPriceOri(Double priceOri) {
		this.priceOri = priceOri;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setSugar(Integer sugar) {
		this.sugar = sugar;
	}
	
	
	public Double getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coffeeId == null) ? 0 : coffeeId.hashCode());
		result = prime * result + ((coffeeType == null) ? 0 : coffeeType.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((discount_price == null) ? 0 : discount_price.hashCode());
		result = prime * result + ((indentId == null) ? 0 : indentId.hashCode());
		result = prime * result + ((isHot == null) ? 0 : isHot.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((payMethod == null) ? 0 : payMethod.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((priceOri == null) ? 0 : priceOri.hashCode());
		result = prime * result + ((sugar == null) ? 0 : sugar.hashCode());
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
		Payindent other = (Payindent) obj;
		if (coffeeId == null) {
			if (other.coffeeId != null)
				return false;
		} else if (!coffeeId.equals(other.coffeeId))
			return false;
		if (coffeeType == null) {
			if (other.coffeeType != null)
				return false;
		} else if (!coffeeType.equals(other.coffeeType))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (discount_price == null) {
			if (other.discount_price != null)
				return false;
		} else if (!discount_price.equals(other.discount_price))
			return false;
		if (indentId == null) {
			if (other.indentId != null)
				return false;
		} else if (!indentId.equals(other.indentId))
			return false;
		if (isHot == null) {
			if (other.isHot != null)
				return false;
		} else if (!isHot.equals(other.isHot))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (payMethod == null) {
			if (other.payMethod != null)
				return false;
		} else if (!payMethod.equals(other.payMethod))
			return false;
		if (payStatus == null) {
			if (other.payStatus != null)
				return false;
		} else if (!payStatus.equals(other.payStatus))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (priceOri == null) {
			if (other.priceOri != null)
				return false;
		} else if (!priceOri.equals(other.priceOri))
			return false;
		if (sugar == null) {
			if (other.sugar != null)
				return false;
		} else if (!sugar.equals(other.sugar))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Payindent [indentId=" + indentId + ", machineId=" + machineId + ", coffeeId=" + coffeeId
				+ ", discount_price=" + discount_price + ", priceOri=" + priceOri + ", price=" + price + ", payMethod="
				+ payMethod + ", payStatus=" + payStatus + ", createTime=" + createTime + ", orderId=" + orderId
				+ ", isHot=" + isHot + ", sugar=" + sugar + ", coffeeType=" + coffeeType + "]";
	}
	
}
