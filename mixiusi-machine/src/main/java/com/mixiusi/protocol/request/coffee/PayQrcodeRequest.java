package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_QRCODE
		+ "" })
public class PayQrcodeRequest extends SingleRequest {
	@PackIndex(0)
	private String coffeeId;
	@PackIndex(1)
	private String dosing;
	@PackIndex(2)
	private short provider;
//	@PackIndex(3)
//	private String buyerId;
	
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeId = unpack.popVarstr();
		dosing = unpack.popVarstr();
		provider = unpack.popShort();
        return null;
    }
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public void setDosing(String dosing) {
		this.dosing = dosing;
	}
	public String getDosing() {
		return dosing;
	}
	public void setProvider(short provider) {
		this.provider = provider;
	}
	public short getProvider() {
		return provider;
	}

}
