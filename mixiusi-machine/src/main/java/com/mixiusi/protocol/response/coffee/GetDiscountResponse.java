package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class GetDiscountResponse extends Response {
	private String favorable;

	public GetDiscountResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
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
		return ICoffeeService.CommandId.GET_DISCOUNT;
	}
	
	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}
	public String getFavorable() {
		return favorable;
	}
}
