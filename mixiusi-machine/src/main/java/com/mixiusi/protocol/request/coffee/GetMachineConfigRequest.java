package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.GET_MACHINE_CONFIG
		+ "" })
public class GetMachineConfigRequest extends Request {

	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		return null;
	}

	
}
