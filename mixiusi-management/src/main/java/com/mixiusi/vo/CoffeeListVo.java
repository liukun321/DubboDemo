package com.mixiusi.vo;

public class CoffeeListVo {
	
	private String indentId;
	private String coffeeId;
	/**
	 * 咖啡的糖度
	 */
	private Integer sugar;
	/**
	 * 咖啡的杯数
	 */
	private Integer cupNum;
	/**
	 * 咖啡原价
	 */
	private Double priceOri;
	/**
	 * 是否打折
	 */
	private boolean discount;
	/**
	 * 折扣价
	 */
	private Double discount_price;
	/**
	 * 咖啡实际价格
	 */
	private Double price;
	
	private Integer coffeeType;
	
	public Integer getCoffeeType() {
		return coffeeType;
	}
	public void setCoffeeType(Integer coffeeType) {
		this.coffeeType = coffeeType;
	}
	public String getIndentId() {
		return indentId;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public Integer getSugar() {
		return sugar;
	}
	public Integer getCupNum() {
		return cupNum;
	}
	public Double getPriceOri() {
		return priceOri;
	}
	public boolean isDiscount() {
		return discount;
	}
	public Double getDiscount_price() {
		return discount_price;
	}
	public Double getPrice() {
		return price;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	public void setSugar(Integer sugar) {
		this.sugar = sugar;
	}
	public void setCupNum(Integer cupNum) {
		this.cupNum = cupNum;
	}
	public void setPriceOri(Double priceOri) {
		this.priceOri = priceOri;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
