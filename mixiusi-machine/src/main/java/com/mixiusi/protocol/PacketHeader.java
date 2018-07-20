package com.mixiusi.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.mixiusi.protocol.marshal.Marshallable;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class PacketHeader implements Marshallable {
	public final static byte TRUNKED = 1;

	public final static byte COMPRESSED = 2;

	public final static byte RESCODE = 4;

	public final static byte KEY = 8;

	private byte serviceId; // 鏈嶅姟id  #NIU#
	//private short serviceId;

	private byte commandId; // 鍛戒护id
	//private short commandId;

	private short serialId; // 搴忓垪鍙�

	private byte tag;

	private int packetLength;//浠呬綔涓哄瓨鍌ㄩ暱搴︾敤锛屼笉鐢ㄤ簬缃戠粶浼犺緭
	
//	private byte[] length;
	
	// 浠ヤ笅鏄彲鍙樺寘澶�
	private short resCode;

	private String key = "";

	public PacketHeader() {
		tag = 0;
	}

	public PacketHeader(byte serviceId, byte commandId) {

		tag = 0;
		this.serviceId = serviceId;
		this.commandId = commandId;
	}

	public PacketHeader duplicate() {
		PacketHeader rh = new PacketHeader();
		rh.serviceId = this.serviceId;
		rh.commandId = this.commandId;
		rh.serialId = this.serialId;
		rh.tag = this.tag;
//		rh.length = Arrays.copyOf(length, length.length);
		rh.packetLength = this.packetLength;
		rh.resCode = this.resCode;
		rh.key = this.key;
		return rh;
	}

	public void marshal(Pack p) {
		p.putFetch(lengthToBytes(packetLength));
		p.putByte(serviceId);
		p.putByte(commandId);  
		p.putShort(serialId);
		p.putByte(tag);
		if (hasRescode()) {
			p.putShort(resCode);
		}
		if (hasKey()) {
			p.putVarstr(key);
		}
	}

	public void unmarshal(Unpack p) {
		int i = 0;
		ByteBuffer bb = ByteBuffer.allocate(4);
		for (; i < 4; i++) {
			byte b = p.popByte();
			bb.put(b);
			if ((b & 0x80) == 0) {
				break;
			}
		}
		bb.flip();
		this.packetLength = bytesToLength(bb.array());
		
		this.serviceId = p.popByte();
		this.commandId = p.popByte();

		this.serialId = p.popShort();
		this.tag = p.popByte();
		if (hasRescode()) {
			this.resCode = p.popShort();
		}
		if (hasKey()) {
			this.key = p.popVarstr();
		}

	}

	public boolean isTrunked() {
		return 0 != (tag & TRUNKED);
	}

	public boolean isCompressed() {
		return 0 != (tag & COMPRESSED);
	}

	public boolean hasRescode() {
		return 0 != (tag & RESCODE);
	}

	public boolean hasKey() {
		return 0 != (tag & KEY);
	}

	public void setTrunked() {
		tag |= TRUNKED;
	}

	public void setCompressed() {
		tag |= COMPRESSED;
	}

	public void setRescodeFlag() {
		tag |= RESCODE;
	}

	public void setKeyFlag() {
		tag |= KEY;
	}

	public void clearTrunked() {
		tag &= ~TRUNKED;
	}

	public void clearCompressed() {
		tag &= ~COMPRESSED;
	}
	
	public void clearRescodeFlag() {
		tag &= ~RESCODE;
	}

	public void clearKeyFlag() {
		tag &= ~KEY;
	}

	public int size() {
		int fixLength = 5;
		if (hasRescode()) {
			fixLength += 2;
		}
		if (hasKey()) {
			try {
				fixLength += key.getBytes("UTF-8").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();// impossible
			}
			fixLength += 2;
		}

		return fixLength;
	}
	
//	public short getServiceId() {  //#NIU#
//		return serviceId;
//	}

	public byte getServiceId() {  //#NIU#
		return serviceId;
	}

	
//	public void setServiceId(short serviceId) {
//		this.serviceId = serviceId;
//	}
	
	public void setServiceId(byte serviceId) {
		this.serviceId = serviceId;
	}
	
	public short getCommandId() {
		return commandId;
	}

//	public byte getCommandId() {
//		return commandId;
//	}
	
//	public void setCommandId(short commandId) {
//		this.commandId = commandId;
//	}

	public void setCommandId(byte commandId) {
		this.commandId = commandId;
	}

	public short getSerialId() {
		return serialId;
	}

	public void setSerialId(short serialId) {
		this.serialId = serialId;
	}

	public static int bytesToLength(byte[] bytes) {
		int multiplier = 1;
		int value = 0;
		int i = 0;
		byte digit;
		do {
			digit = bytes[i];
			value += (digit & 127) * multiplier;
			multiplier *= 128;
			i++;
		} while ((digit & 128) != 0);

		return value;
	}

	public static byte[] lengthToBytes(int size) {
		byte[] ret = new byte[4];
		int idx = 0;
		byte b = 0;
		do {
			b = (byte) (size % 128);
			size = size / 128;
			if (size > 0) {
				b |= 0x80;
			}
			ret[idx++] = b;
		} while (size > 0);
		
		byte[] real = new byte[idx];
		System.arraycopy(ret, 0, real, 0, idx);
		return real;
	}
	
	public static int getByteCount(int size) {
		int count = 0;
		do {
			size = size / 128;
			count++;
		} while (size > 0);
		
		return count;
	}

	public short getResCode() {
		return resCode;
	}

	public void setResCode(short resCode) {
		this.resCode = resCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public byte getTag() {
		return tag;
	}

	public int getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}
	
	public String toString() {
		return "PacketHeader [SID " + serviceId + " , CID " + commandId + " , SER "
				+ serialId + " , RES " + resCode + " , TAG " + tag + " , KEY "
				+ key + " , LEN " + getPacketLength() + "]";
	}

	public static void main(String... args) {
		PacketHeader ph = new PacketHeader();
		ph.setCommandId((byte) 1);
		ph.setPacketLength(1 );
		ph.setSerialId((byte) 100);
		ph.setServiceId((byte) 22);
		
		Pack p = new Pack();
		ph.marshal(p);
		
		Unpack up = new Unpack(p.getBuffer());
		
		PacketHeader ph2 = new PacketHeader();
		ph2.unmarshal(up);
		System.out.println(ph2.getPacketLength()+"\n"+ph2);
	}
	
	public LinkFrame toOldStyleHeader() {
		LinkFrame lf = new LinkFrame();
		lf.serviceId = this.serviceId;
		lf.commandId = this.commandId;
		lf.destination = "";
		lf.forward = 0;
		lf.key = this.hasKey() ? this.key : "";
		lf.length = packetLength + 4;
		lf.resCode = resCode;
		lf.serialId = serialId;
		lf.tag = tag;
		lf.version = 0;
		return lf;
	}
	
}
