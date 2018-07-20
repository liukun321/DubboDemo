package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.MACHINE_INIT
        + "" })
public class CoffeeMachineInitRequest extends Request {
	@PackIndex(0)
	private int userID;
	@PackIndex(1)
	private String inventory;
	//TODO  咖啡机初始化信息
	
    @Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
    	userID = unpack.popInt();
    	inventory = unpack.popVarstr();
        return null;
    }
    public void setInventory(String inventory) {
		this.inventory = inventory;
	}
    public String getInventory() {
		return inventory;
	}
    public void setUserID(int userID) {
		this.userID = userID;
	}
    public int getUserID() {
		return userID;
	}

}
