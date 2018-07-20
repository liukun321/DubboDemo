package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;

public class PayQrcodeCartResponse extends Response {
	private String payIndent;

	private String coffeeIndents;

	private String price;

	private String priceOri;

	private String qrcodeUrl;
	
	public PayQrcodeCartResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putVarstr(payIndent);
        pack.putVarstr(coffeeIndents);
        pack.putVarstr(price);
        pack.putVarstr(priceOri);
        pack.putVarstr(qrcodeUrl);
       
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_QRCODE_CART;
	}
	public String getCoffeeIndents() {
		return coffeeIndents;
	}
	public void setCoffeeIndents(String coffeeIndents) {
		this.coffeeIndents = coffeeIndents;
	}
	public String getPayIndent() {
		return payIndent;
	}
	public void setPayIndent(String payIndent) {
		this.payIndent = payIndent;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceOri() {
		return priceOri;
	}
	public void setPriceOri(String priceOri) {
		this.priceOri = priceOri;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	
}
