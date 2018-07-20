package com.mixiusi.protocol.marshal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;


public class Property implements Marshallable {
	public Map<Integer, String> props = new HashMap<Integer, String>();

	public static Property unpack(Unpack p) {
		Property props = new Property();
		props.unmarshal(p);
		return props;
	}

	public void marshal(Pack p) {
		p.putInt(props.size());
		for (Iterator<Integer> it = iterator(); it.hasNext();) {
			int tag = it.next();
			p.putInt(tag);
			p.putVarstr(get(tag));
		}
	}

	public void unmarshal(Unpack p) {
		int cnt = p.popInt();
		for (int i = 0; i < cnt; ++i) {
			int tag = p.popInt();
			put(tag, p.popVarstr());
		}
	}

	public boolean equals(Property prop) {
		return this.props.equals(prop.props);
	}

	public Iterator<Integer> iterator() {
		return props.keySet().iterator();
	}

	public String get(Integer tag) {
		return props.get(tag);
	}

	public void put(Integer tag, String value) {
		props.put(tag, value == null ? "" : value);
	}

	public int getInteger(Integer tag) {
		String value = props.get(tag);
		if (value == null || value.equals(""))
			return 0;
		return Integer.parseInt(value);
	}

	public void putInteger(Integer tag, int value) {
		props.put(tag, "" + value);
	}

	public int size() {
		return props.size();
	}

	public void clear() {
		props.clear();
	}

	public Property duplicate() {
		Property ret = new Property();

		for (Integer key : props.keySet()) {
			String dstItem = new String(get(key));
			ret.put(key, dstItem);
		}

		return ret;
	}

	public String toString() {
		return props.toString();
	}
	
	public boolean contains(Integer tag) {
		return props.containsKey(tag);
	}

	/*
	 * public Property clone() { Property newProp = new Property();
	 * newProp.props = new HashMap<Integer, String>( this.props ); return
	 * newProp; }
	 */
}