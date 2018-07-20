package com.mixiusi.protocol.pack;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.mixiusi.bean.utils.BufferManager;
import com.mixiusi.protocol.marshal.Marshallable;

public class Pack {
	private ByteBuffer buffer;

	private int capacity = 1024 * 1024;

	public Pack() {
		buffer = BufferManager.allocate(1024);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	public int size() {
		return buffer.position();
	}

	/**
	 * Get buffer of Pack with flip()
	 */
	public ByteBuffer getBuffer() {
		ByteBuffer dup = buffer.duplicate();
		dup.flip();
		return dup;
	}

	public Pack putFetch(byte[] bytes) {
		try {
			
			ensureCapacity(bytes.length);
			buffer.put(bytes);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putByte(byte bt) {
		try {
			ensureCapacity(1);
			buffer.put(bt);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putVarstr(String str) {
		try {
			return putVarbin(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException codeEx) {
			throw new PackException();
		}
	}

	public Pack putInt(int val) {
		try {
			ensureCapacity(4);
			buffer.putInt(val);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putBoolean(boolean val) {
		try {
			ensureCapacity(1);
			buffer.put((byte) (val ? 1 : 0));
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putLong(long val) {
		try {
			ensureCapacity(8);
			buffer.putLong(val);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putShort(short val) {
		try {
			ensureCapacity(2);
			buffer.putShort(val);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	// 16位的
	public Pack putVarbin(byte[] bytes) {
		try {
			ensureCapacity(2 + bytes.length);
			buffer.putShort((short) bytes.length);
			buffer.put(bytes);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	// 32位的
	public Pack putVarbin32(byte[] bytes) {
		try {
			ensureCapacity(4 + bytes.length);
			buffer.putInt((int) bytes.length);
			buffer.put(bytes);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public Pack putMarshallable(Marshallable mar) {
		mar.marshal(this);
		return this;
	}

	public Pack putBuffer(ByteBuffer buf) {
		try {
			ensureCapacity(buf.remaining());
			buffer.put(buf);
			return this;
		} catch (BufferOverflowException bex) {
			throw new PackException();
		}
	}

	public void replaceShort(int off, short val) {
		try {
			int pos = buffer.position();
			buffer.position(off);
			buffer.putShort(val).position(pos);
		} catch (BufferOverflowException bex) {
			throw new PackException();
		} catch (IllegalArgumentException iex) {
			throw new PackException();
		}
	}

	public void replaceInt(int off, int val) {
		try {
			int pos = buffer.position();
			buffer.position(off);
			buffer.putInt(val).position(pos);
		} catch (BufferOverflowException bex) {
			throw new PackException();
		} catch (IllegalArgumentException iex) {
			throw new PackException();
		}
	}

	/**
	 * Ensures that a the buffer can hold up the new increment
	 * 
	 * @throws Exception
	 */
	public void ensureCapacity(int increament) throws BufferOverflowException {
		if (buffer.remaining() >= increament) {
			return;
		}

		int requiredCapacity = buffer.capacity() + increament
				- buffer.remaining();

		if (requiredCapacity > capacity) {
			throw new BufferOverflowException();
		}

		int tmp = Math.max(requiredCapacity, buffer.capacity() * 2);
		int newCapacity = Math.min(tmp, capacity);

		ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity);
		newBuffer.order(buffer.order());
		buffer.flip();
		newBuffer.put(buffer);
		buffer = newBuffer;
	}

	public String toString() {
		return buffer.toString() + " Size " + size();
	}

}