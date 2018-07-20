package com.mixiusi.bean.vo;

import java.io.Serializable;

public class WechatUserVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7424206946175195047L;
	private String openId;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}
