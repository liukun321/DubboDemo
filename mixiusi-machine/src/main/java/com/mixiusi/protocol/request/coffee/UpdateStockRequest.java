package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.UPDATE_STOCK
        + "" })
public class UpdateStockRequest extends SingleRequest {
	@PackIndex(1)
	private String inventory;
	
   @Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
	   inventory = unpack.popVarstr();
	   return null;
    }
    public String getInventory(){
    	return this.inventory;
    }
    public void setInventory(String inventory){
    	this.inventory = inventory;
    }

}
