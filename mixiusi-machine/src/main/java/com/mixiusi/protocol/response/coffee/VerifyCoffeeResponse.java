package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class VerifyCoffeeResponse extends Response {
	private int coffeeId;
	private String dosingContent;
	private long timestamp;
	
	public VerifyCoffeeResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putInt(coffeeId);
        pack.putVarstr(dosingContent);
        pack.putLong(timestamp);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.VERIFY_CODE;
	}

	public int getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(int coffeeId) {
		this.coffeeId = coffeeId;
	}
	public String getDosingContent() {
		return dosingContent;
	}
	public void setDosingContent(String dosingContent) {
		this.dosingContent = dosingContent;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
