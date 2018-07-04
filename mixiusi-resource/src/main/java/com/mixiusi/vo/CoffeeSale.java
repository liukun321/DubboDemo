package com.mixiusi.vo;

public class CoffeeSale {
	private String coffeeId;
	private String coffeeName;
	/**
	 * 原价
	 */
	private Double price;
	/**
	 * 折后价
	 */
	private Double discount_price;
	private String imgurl;
	private Boolean is_new = false;
	private Boolean isSugar;
	/**
	 * 是否打折
	 */
	private Boolean discount;
	
	private Long saleSum;

	public String getCoffeeId() {
		return coffeeId;
	}

	public String getCoffeeName() {
		return coffeeName;
	}

	public Double getPrice() {
		return price;
	}

	public Double getDiscount_price() {
		return discount_price;
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

	public Boolean getDiscount() {
		return discount;
	}

	public Long getSaleSum() {
		return saleSum;
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

	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
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

	public void setDiscount(Boolean discount) {
		this.discount = discount;
	}

	public void setSaleSum(Long saleSum) {
		this.saleSum = saleSum;
	}
	
}
