package com.mixiusi.protocol.request;

import com.mixiusi.protocol.pack.Unpack;

public class SingleRequest extends Request {

	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		unpack(unpack);
		return null;
	}

}
