package com.mixiusi.bean.vo;

import java.io.Serializable;

public class CoffeeVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6528472657401006835L;
	private String coffeeId;
	
	public CoffeeVo() {
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	
}
