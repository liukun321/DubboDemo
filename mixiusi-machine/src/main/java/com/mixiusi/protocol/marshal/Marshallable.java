package com.mixiusi.protocol.marshal;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public interface Marshallable {
	public abstract void marshal(Pack pack);

	public abstract void unmarshal(Unpack unpack);
}
