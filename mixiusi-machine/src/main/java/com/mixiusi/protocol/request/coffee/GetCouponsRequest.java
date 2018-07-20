package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.GET_COUPONS
		+ "" })
public class GetCouponsRequest extends Request {
	//优惠券
	@PackIndex(1)
	private String couponCode;
		
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		couponCode = unpack.popVarstr();
        return null;
    }

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	
}
