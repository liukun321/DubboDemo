package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ROLL_BACK
		+ "" })
public class RollbackRequest extends SingleRequest {
	@PackIndex(0)
	private String coffeeIndent;
	@PackIndex(1)
	private long timestamp;


	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeIndent = unpack.popVarstr();
		timestamp = unpack.popLong();

		return null;
	}
	public void setCoffeeIndent(String coffeeIndent) {
		this.coffeeIndent = coffeeIndent;
	}
	public String getCoffeeIndent() {
		return coffeeIndent;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getTimestamp() {
		return timestamp;
	}

	
}
