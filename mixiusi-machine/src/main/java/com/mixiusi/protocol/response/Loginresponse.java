package com.mixiusi.protocol.response;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.IAuthService;
import com.mixiusi.protocol.pack.Pack;

public class Loginresponse extends Response {

	private short status;
	
	private String sessionId;
    
    private String vendorName;
	
	public Loginresponse(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putShort(status);
		p.putVarstr(sessionId);
		p.putVarstr(vendorName);
		return p;
	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_AUTH;
	}

	@Override
	public short getCommandId() {
		return IAuthService.CommandId.CID_LOGIN;
	}

	public short getstatus() {
		return status;
	}

	public void setstatus(short status){
		this.status = status;
	}
	
	public String getsessionId(){
		return sessionId;
	}
	public void setsessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getvendorname(String vendorname){
		return vendorname;
	}
	public void setvendorname(String vendorname){
		this.vendorName = vendorname;
	}
}
