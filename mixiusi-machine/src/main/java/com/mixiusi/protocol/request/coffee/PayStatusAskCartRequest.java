package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ASK_PAY_STATUS_CART
		+ "" })
public class PayStatusAskCartRequest extends Request {
	@PackIndex(0)
	private String payIndent;
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		payIndent = unpack.popVarstr();
		return null;
	}
	public String getPayIndent() {
		return payIndent;
	}
	public void setPayIndent(String payIndent) {
		this.payIndent = payIndent;
	}
}
