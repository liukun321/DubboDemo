package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.marshal.ArrayMable;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class GetCoffeeResponse extends Response {
	private ArrayMable coffeeInfos;
//	private String favorable;
	
	public GetCoffeeResponse(String uid) {
		super(uid);
	}
	
	public void setarraylen(int size){
		coffeeInfos = new ArrayMable(size);
	}
	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putMarshallable(coffeeInfos);
//		p.putVarstr(favorable);
		return p;
	}
	
	public void setCoffeeInfos(ArrayMable coffeeInfos){
		this.coffeeInfos = coffeeInfos;
	}
	
	public ArrayMable getCoffeeInfos(){
		return this.coffeeInfos;
	}
	
//	public void setFavorable(String favorable) {
//		this.favorable = favorable;
//	}
//	
//	public String getFavorable() {
//		return favorable;
//	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_COFFEE;
	}
}
