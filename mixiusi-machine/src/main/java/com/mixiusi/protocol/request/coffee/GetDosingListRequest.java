package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.GET_DOSING
		+ "" })

public class GetDosingListRequest extends Request {

    
	
	public Unpack unpackBody(Unpack unpack) throws Exception {
        /*setCoffeeDosingList(new ArrayMable(Property.class));
        coffeeDosingList.unmarshal(unpack);*/
		return null;
	}

    /*public ArrayMable getCoffeeDosingList() {
        return coffeeDosingList;
    }*/

    /*public void setCoffeeDosingList(ArrayMable coffeeDosingList) {
        this.coffeeDosingList = coffeeDosingList;
    }*/
}
