package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.response.Response;

public class AppDownloadResponse extends Response {
	@PackIndex(0)
	private String downloadUrl;
	
	public AppDownloadResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putVarstr(downloadUrl);
        return pack;
    }

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.APP_DOWNLOAD;
	}
}
