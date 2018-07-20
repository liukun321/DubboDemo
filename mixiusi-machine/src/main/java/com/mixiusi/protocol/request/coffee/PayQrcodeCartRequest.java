package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_QRCODE_CART
		+ "" })
public class PayQrcodeCartRequest extends SingleRequest {

	@PackIndex(0)
	private String coffeeIndents;
	@PackIndex(1)
	private short provider;
	
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeIndents = unpack.popVarstr();
		provider = unpack.popShort();
        return null;
    }
	
	public String getCoffeeIndents() {
		return coffeeIndents;
	}
	
	public void setCoffeeIndents(String coffeeIndents) {
		this.coffeeIndents = coffeeIndents;
	}
	public short getProvider() {
		return provider;
	}
	
	public void setProvider(short provider) {
		this.provider = provider;
	}

}
