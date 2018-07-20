package com.mixiusi.protocol.marshal;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class Strmable implements Marshallable {
	public String string = "";

	public Strmable(String str) {
		string = str;
	}

	public Strmable() {
		string = "";
	}

	public void marshal(Pack p) {
		p.putVarstr(string);
	}

	public void unmarshal(Unpack up) {
		string = up.popVarstr();
	}
}
