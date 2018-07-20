package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class PayQrcodeResponse extends Response {
	private String coffeeIndent;
	private String qrcodeUrl;
	private String price;
	
	public PayQrcodeResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putVarstr(coffeeIndent);
        pack.putVarstr(qrcodeUrl);
        pack.putVarstr(price);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_QRCODE;
	}
	public String getCoffeeIndent() {
		return coffeeIndent;
	}
	public void setCoffeeIndent(String coffeeIndent) {
		this.coffeeIndent = coffeeIndent;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}


}
