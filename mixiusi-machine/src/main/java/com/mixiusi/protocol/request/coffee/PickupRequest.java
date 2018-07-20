package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PICK_UP
		+ "" })
public class PickupRequest extends SingleRequest {

	@PackIndex(1)
	private String pickupCode;
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		pickupCode = unpack.popVarstr();
		return null;
	}
	public String getPickupCode() {
		return pickupCode;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	
}
