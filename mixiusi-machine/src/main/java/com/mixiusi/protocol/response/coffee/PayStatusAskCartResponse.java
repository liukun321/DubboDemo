package com.mixiusi.protocol.response.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.response.Response;
/**
 * 查询支付状态
 * @author liukun
 *
 */
public class PayStatusAskCartResponse extends Response {
	private int payStatus;
	public PayStatusAskCartResponse(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	@Override
    public Pack packResponse() {
        Pack pack = new Pack();
        pack.putInt(payStatus);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ASK_PAY_STATUS_CART;
	}


	public int getPayStatus() {
		return payStatus;
	}


	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	

}
