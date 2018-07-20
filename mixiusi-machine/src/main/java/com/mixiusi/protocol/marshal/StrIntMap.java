package com.mixiusi.protocol.marshal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class StrIntMap implements Marshallable {
	public Map<String, Integer> m_map = new HashMap<String, Integer>();

	public static StrIntMap unpack(Unpack p) {
		StrIntMap map = new StrIntMap();
		map.unmarshal(p);
		return map;
	}

	public void marshal(Pack p) {
		p.putInt(m_map.size());
		for (Iterator<String> it = iterator(); it.hasNext();) {
			String str = it.next();
			p.putVarstr(str);
			p.putInt(get(str));
		}
	}

	public void unmarshal(Unpack p) {
		int cnt = p.popInt();
		for (int i = 0; i < cnt; ++i) {
			String str = p.popVarstr();
			put(str, p.popInt());
		}
	}

	public boolean equals(StrIntMap smap) {
		return this.m_map.equals(smap.m_map);
	}

	public Iterator<String> iterator() {
		return m_map.keySet().iterator();
	}

	public int get(String str) {
		Integer n = m_map.get(str);
		if (n == null)
			return 0;
		else
			return n;
	}

	public void put(String str, Integer value) {
		m_map.put(str, value == null ? 0 : value);
	}

	public int size() {
		return m_map.size();
	}

}