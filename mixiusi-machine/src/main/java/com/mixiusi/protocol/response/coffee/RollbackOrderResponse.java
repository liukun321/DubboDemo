package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class RollbackOrderResponse extends Response {

	public RollbackOrderResponse(String uid) {
		super(uid);
	}

	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ROLL_BACK_CART;
	}

}
