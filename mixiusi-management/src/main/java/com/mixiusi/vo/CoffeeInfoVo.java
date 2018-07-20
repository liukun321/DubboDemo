package com.mixiusi.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class CoffeeInfoVo {
	/**
	 * 咖啡机编号
	 */
	@NotEmpty
	private String coffeeNumber;
	private String coffeeId;
	@NotEmpty
	private String coffeeName;
	@NotEmpty
	private Double price;
	private String imgurl;
	@NotEmpty
	private Boolean is_new;
	private Boolean isSugar;
	/**
	 * 折后价
	 */
	@NotEmpty
	private Double discount_price;
	/**
	 * 是否打折
	 */
	private Boolean discount;


	public String getCoffeeId() {
		return coffeeId;
	}


	public String getCoffeeName() {
		return coffeeName;
	}


	public Double getPrice() {
		return price;
	}


	public String getImgurl() {
		return imgurl;
	}


	public Boolean getIs_new() {
		return is_new;
	}

	public Boolean getIsSugar() {
		return isSugar;
	}


	public Double getDiscount_price() {
		return discount_price;
	}


	public Boolean getDiscount() {
		return discount;
	}


	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}


	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}


	public void setIs_new(Boolean is_new) {
		this.is_new = is_new;
	}

	public void setIsSugar(Boolean isSugar) {
		this.isSugar = isSugar;
	}


	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}


	public void setDiscount(Boolean discount) {
		this.discount = discount;
	}

	public String getCoffeeNumber() {
		return coffeeNumber;
	}


	public void setCoffeeNumber(String coffeeNumber) {
		this.coffeeNumber = coffeeNumber;
	}


	@Override
	public String toString() {
		return "CoffeeInfoVo [coffeeNumber=" + coffeeNumber + ", coffeeId=" + coffeeId + ", coffeeName=" + coffeeName
				+ ", price=" + price + ", imgurl=" + imgurl + ", is_new=" + is_new + ", isSugar=" + isSugar
				+ ", discount_price=" + discount_price + ", discount=" + discount + "]";
	}

}
