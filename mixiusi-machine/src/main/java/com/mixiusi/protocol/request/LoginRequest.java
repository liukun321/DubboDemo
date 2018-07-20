package com.mixiusi.protocol.request;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.IAuthService;
import com.mixiusi.protocol.pack.PackIndex;

@RequestID(service = ServiceID.SVID_LITE_AUTH, command = { IAuthService.CommandId.CID_LOGIN
		+ "" })
public class LoginRequest extends SingleRequest {

	@PackIndex(0)
	private String uid;
	@PackIndex(1)
	private String password;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

   
}
