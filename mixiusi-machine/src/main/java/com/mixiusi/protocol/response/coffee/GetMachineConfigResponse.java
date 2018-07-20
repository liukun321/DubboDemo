package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class GetMachineConfigResponse extends Response {
	private String workTemp;
	private String keepTemp;
	private String washTime;

	public GetMachineConfigResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pack packResponse() {
		Pack p = new Pack();
		p.putVarstr(workTemp);
		p.putVarstr(keepTemp);
		p.putVarstr(washTime);
		return p;
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_MACHINE_CONFIG;
	}
	public void setWorkTemp(String worktemp){
		this.workTemp = worktemp;
	}
	public String getWorkTemp(){
		return this.workTemp;
	}
	public void setKeepTemp(String keepTemp){
		this.keepTemp = keepTemp;
	}
	public String getKeepTemp(){
		return this.keepTemp;
	}
	public void setWashTime(String washTime){
		this.washTime = washTime;
	}
	public String getWashTime(){
		return this.washTime;
	}
}
