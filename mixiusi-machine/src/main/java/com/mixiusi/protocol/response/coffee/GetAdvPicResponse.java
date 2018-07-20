package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class GetAdvPicResponse extends Response {
	private String advPics;

	public GetAdvPicResponse(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putVarstr(advPics);
	
		return p;
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_ADV_PIC;
	}
	
	public void setAdvPics(String advpics){
		this.advPics = advpics;
	}
	public String getAdvPics(){
		return this.advPics;
	}
	
}
