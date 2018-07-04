package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "coffeeinfo")
public class CoffeeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6842830008281182067L;
	@Id
	private String coffeeId;
	@NotNull
	private String coffeeName;
	/**
	 * 原价
	 */
	@NotNull
	private Double price;
	/**
	 * 折后价
	 */
	@NotNull
	private Double discount_price;
	@NotNull
	private String imgurl;
	@NotNull
	private Boolean is_new;
	@NotNull
	private Boolean isSugar;
	/**
	 * 是否打折
	 */
	private Boolean discount;
	
	public CoffeeInfo() {
	}

	public String getCoffeeId() {
		return coffeeId;
	}

	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getCoffeeName() {
		return coffeeName;
	}

	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}

	public Boolean getIsSugar() {
		return isSugar;
	}

	public void setIsSugar(Boolean isSugar) {
		this.isSugar = isSugar;
	}

	public Boolean getIs_new() {
		return is_new;
	}

	public void setIs_new(Boolean is_new) {
		this.is_new = is_new;
	}

	public Boolean getDiscount() {
		return discount;
	}

	public void setDiscount(Boolean discount) {
		this.discount = discount;
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
		result = prime * result + ((coffeeName == null) ? 0 : coffeeName.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((discount_price == null) ? 0 : discount_price.hashCode());
		result = prime * result + ((imgurl == null) ? 0 : imgurl.hashCode());
		result = prime * result + ((isSugar == null) ? 0 : isSugar.hashCode());
		result = prime * result + ((is_new == null) ? 0 : is_new.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		CoffeeInfo other = (CoffeeInfo) obj;
		if (coffeeId == null) {
			if (other.coffeeId != null)
				return false;
		} else if (!coffeeId.equals(other.coffeeId))
			return false;
		if (coffeeName == null) {
			if (other.coffeeName != null)
				return false;
		} else if (!coffeeName.equals(other.coffeeName))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (discount_price == null) {
			if (other.discount_price != null)
				return false;
		} else if (!discount_price.equals(other.discount_price))
			return false;
		if (imgurl == null) {
			if (other.imgurl != null)
				return false;
		} else if (!imgurl.equals(other.imgurl))
			return false;
		if (isSugar == null) {
			if (other.isSugar != null)
				return false;
		} else if (!isSugar.equals(other.isSugar))
			return false;
		if (is_new == null) {
			if (other.is_new != null)
				return false;
		} else if (!is_new.equals(other.is_new))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CoffeeInfo [coffeeId=" + coffeeId + ", coffeeName=" + coffeeName + ", price=" + price
				+ ", discount_price=" + discount_price + ", imgurl=" + imgurl + ", is_new=" + is_new + ", isSugar="
				+ isSugar + ", discount=" + discount + "]";
	}

}
