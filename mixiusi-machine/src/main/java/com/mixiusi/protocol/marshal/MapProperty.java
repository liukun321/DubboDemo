package com.mixiusi.protocol.marshal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class MapProperty implements Marshallable {

	public Map<String, Property> mapp = new HashMap<String, Property>();

	public static MapProperty unpack(Unpack p) {
		MapProperty props = new MapProperty();
		props.unmarshal(p);
		return props;
	}

	public void marshal(Pack p) {
		p.putInt(size());
		for (Iterator<String> it = iterator(); it.hasNext();) {
			String key = it.next();
			p.putVarstr(key);
			get(key).marshal(p);
		}
	}

	public void unmarshal(Unpack p) {
		int cnt = p.popInt();
		for (int i = 0; i < cnt; i++) {
			String key = p.popVarstr();
			Property props = new Property();
			props.unmarshal(p);
			put(key, props);
		}

	}

	public void trace() {
	}

	public Iterator<String> iterator() {
		return mapp.keySet().iterator();
	}

	public Property get(String uid) {
		return mapp.get(uid);
	}

	public void put(String uid, Property value) {
		mapp.put(uid, value);
	}

	public int size() {
		return mapp.size();
	}

	public void clear() {
		mapp.clear();
	}

	public MapProperty duplicate() {
		MapProperty ret = new MapProperty();

		for (String key : mapp.keySet()) {
			Property dstItem = mapp.get(key).duplicate();
			ret.put(key, dstItem);
		}

		return ret;
	}

	public String toString() {
		return "size:" + size() + "\r\n" + mapp.toString();
	}

}
