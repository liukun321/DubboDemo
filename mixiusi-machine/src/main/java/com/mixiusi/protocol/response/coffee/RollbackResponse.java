package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class RollbackResponse extends Response {
	private long rbTimestamp;
	private long qhTimestamp;
	
	public RollbackResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putLong(rbTimestamp);
        pack.putLong(qhTimestamp);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ROLL_BACK;
	}
	
	public void setQhTimestamp(long qhTimestamp) {
		this.qhTimestamp = qhTimestamp;
	}
	public long getQhTimestamp() {
		return qhTimestamp;
	}
	public void setRbTimestamp(long rbTimestamp) {
		this.rbTimestamp = rbTimestamp;
	}
	public long getRbTimestamp() {
		return rbTimestamp;
	}


}
