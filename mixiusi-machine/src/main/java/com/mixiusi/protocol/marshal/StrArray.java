package com.mixiusi.protocol.marshal;

import java.util.ArrayList;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class StrArray implements Marshallable {
	public ArrayList<String> strings = new ArrayList<String>();

	public int size() {
		return strings.size();
	}

	public StrArray() {
		strings = new ArrayList<String>();
	}

	public StrArray(String[] strArray) {
		strings = new ArrayList<String>(strArray.length);

		for (String s : strArray)
			strings.add(s);
	}

	public StrArray(ArrayList<String> strArray) {
		strings = strArray;
	}

	public void add(String s) {
		strings.add(s);
	}

	public void marshal(Pack p) {
		p.putInt(strings.size());

		for (int i = 0; i < strings.size(); ++i)
			p.putVarstr(strings.get(i));
	}

	public void unmarshal(Unpack up) {
		int len = up.popInt();
		strings = new ArrayList<String>(len);

		for (int i = 0; i < len; ++i)
			strings.add(up.popVarstr());
	}
}
