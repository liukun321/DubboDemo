package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.marshal.ArrayMable;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class PickupResponse extends Response {
	private ArrayMable coffeeInfos;

	public PickupResponse(String uid) {
		super(uid);
	}
	
	public void setarraylen(int size){
		coffeeInfos = new ArrayMable(size);
	}
		
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putMarshallable(coffeeInfos);
        return pack;
    }


	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}


	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PICK_UP;
	}


	public ArrayMable getCoffeeInfos() {
		return coffeeInfos;
	}

	public void setCoffeeInfos(ArrayMable coffeeInfos) {
		this.coffeeInfos = coffeeInfos;
	}

}
