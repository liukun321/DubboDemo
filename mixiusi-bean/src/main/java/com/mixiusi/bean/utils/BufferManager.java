package com.mixiusi.bean.utils;

import java.nio.ByteBuffer;

public class BufferManager {
	static BufferManager m_instance = new BufferManager();

	public static ByteBuffer allocate() {
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		return buffer;
	}

	public static ByteBuffer allocate(int size) {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		return buffer;
	}

	public static void disponse(ByteBuffer buffer) {
	}
}
