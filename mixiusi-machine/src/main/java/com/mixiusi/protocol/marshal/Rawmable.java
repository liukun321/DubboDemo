package com.mixiusi.protocol.marshal;

import java.nio.ByteBuffer;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class Rawmable implements Marshallable {
	private byte[] m_bytes;

	/*
	 * src [ position : limit ] -> bytes
	 */
	public Rawmable(ByteBuffer src) {
		int size = src.remaining();
		m_bytes = new byte[size];
		src.get(m_bytes, 0, size);
	}

	public Rawmable(byte[] bytes) {
		m_bytes = bytes;
	}

	public Rawmable() {
		m_bytes = new byte[0];
	}

	public void marshal(Pack p) {
		// 鏄惁鍔犱笂瀛楄妭鏁扮粍闀垮害淇℃伅锛� rhythm
		// 涓嶈�冭檻锛屽洜涓篟awmable灏辨槸鍘熷鏁版嵁 淇炵敳瀛�
		// p.putInt( m_bytes.length );
		p.putFetch(m_bytes);
	}

	public void unmarshal(Unpack p) {
		// 鐜板湪鏄皢Unpack涓墿浣欑殑瀛楄妭閮借В鍑猴紝鏄惁瑕佽�冭檻鏁扮粍闀垮害锛�
		// 涓嶈�冭檻锛� 鍥犱负Rawmable灏辨槸鍘熷鏁版嵁 淇炵敳瀛�
		// int size = p.popInt();

		ByteBuffer buf = p.getBuffer();
		int size = buf.remaining();
		m_bytes = p.popFetch(size);
	}

	public void setBytes(byte[] bytes) {
		this.m_bytes = bytes;
	}

	public byte[] getBytes() {
		return m_bytes;
	}
}
