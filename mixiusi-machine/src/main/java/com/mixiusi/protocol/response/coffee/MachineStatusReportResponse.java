package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class MachineStatusReportResponse extends Response {

	public MachineStatusReportResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
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
		return ICoffeeService.CommandId.MACHINE_STATUS_REPORT;
	}
}
