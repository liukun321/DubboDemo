package com.mixiusi.vo;

public class MobileUserVo {
	private String openId;
	/**
	 * 用户 积分
	 */
	private Integer credit;
	private Long totoalordernumber; //用户订单总数
 	private Double totalPrice;//总计消费金额
 	private Long couponnumber;//优惠券数量
	public String getOpenId() {
		return openId;
	}
	public Integer getCredit() {
		return credit;
	}
	public Long getTotoalordernumber() {
		return totoalordernumber;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public Long getCouponnumber() {
		return couponnumber;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public void setTotoalordernumber(Long totoalordernumber) {
		this.totoalordernumber = totoalordernumber;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setCouponnumber(Long couponnumber) {
		this.couponnumber = couponnumber;
	}
 	
 	
}
