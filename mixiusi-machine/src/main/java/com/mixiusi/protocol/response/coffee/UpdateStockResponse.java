package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.response.Response;;

public class UpdateStockResponse extends Response {
	@PackIndex(0)
	private String inventory;
    public UpdateStockResponse(String uid) {
		super(uid);
	}

	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putVarstr(inventory);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.UPDATE_STOCK;
	}


	public String getInventory() {
		return inventory;
	}


	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
}
