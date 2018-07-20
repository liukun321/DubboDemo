package com.mixiusi.protocol.response;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ILinkService;


public class KeepAliveresponse extends Response {

	public KeepAliveresponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_LINK;
	}

	@Override
	public short getCommandId() {
		return ILinkService.CommandId.CID_REQUEST_HEARTBEAT;
	}

}
