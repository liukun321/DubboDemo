package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;
/**
 * 获取优惠券的响应
 * @author liukun
 *
 */
public class GetCouponsResponse extends Response {
	private String favorable;

	public GetCouponsResponse(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putVarstr(favorable);
		return p;
	}
	
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_COUPONS;
	}

	public String getFavorable() {
		return favorable;
	}

	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}

	
}
