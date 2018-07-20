package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.marshal.ArrayMable;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class GetDosingListResponse extends Response {
	public GetDosingListResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	private ArrayMable coffeeDosingList ;
  
	public void setlistlen(int size) {
		coffeeDosingList = new ArrayMable(size);
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}
	
	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putMarshallable(coffeeDosingList);
		return p;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_DOSING;
	}
	
	public ArrayMable getcoffeeDosingList(){
		
		return coffeeDosingList;
	}
	public void setcoffeeDosingList(ArrayMable coffeeDosingList){
		this.coffeeDosingList = coffeeDosingList;
	}
	
}
