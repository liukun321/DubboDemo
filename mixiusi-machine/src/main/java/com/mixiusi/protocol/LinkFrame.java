package com.mixiusi.protocol;

import com.mixiusi.protocol.marshal.Marshallable;
import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.Unpack;

public class LinkFrame implements Marshallable {
	public final static byte TRUNKED = 1;

	public final static byte COMPRESSED = 2;

	public int length; // LinkFrame长度   

	public short serviceId; // 服务id

	public short commandId; // 命令id

	public short serialId; // 序列号

	public short resCode; // 返回码

	public byte tag;

	public String key = ""; //

	public byte forward = 0; //

	public String destination = ""; // 目的服务器地址
	
	public short version = 0;

	public LinkFrame() {
		tag = 0;
		resCode = 200;
		length = 0;
	}

	public LinkFrame(short serviceId, short commandId,String uid) {

		tag = 0;
		resCode = 200;
		length = 0;
		this.serviceId = serviceId;
		this.commandId = commandId;
		
		this.key = uid;
	}

	public LinkFrame duplicate() {
		LinkFrame lf = new LinkFrame();
		lf.length = this.length;
		lf.serialId = this.serialId;
		lf.serviceId = this.serviceId;
		lf.commandId = this.commandId;
		lf.destination = new String(this.destination);
		lf.forward = this.forward;
		lf.resCode = this.resCode;
		lf.tag = this.tag;
		lf.key = new String(this.key);

		return lf;
	}

	public void marshal(Pack p) {
		p.putInt(length);
		p.putShort(serviceId);
		p.putShort(commandId);
		p.putShort(serialId);
		p.putShort(resCode);
		p.putByte(tag);
		p.putVarstr(key);
		p.putByte(forward);
		p.putVarstr(destination);
		p.putShort(version);
	}

	public void unmarshal(Unpack p) {
		length = p.popInt();
		serviceId = p.popShort();
		commandId = p.popShort();
		serialId = p.popShort();
		resCode = p.popShort();
		tag = p.popByte();
		key = p.popVarstr();
		forward = p.popByte();
		destination = p.popVarstr();
		version = p.popShort();
	}

	public boolean isTrunked() {
		return 0 != (tag & TRUNKED);
	}

	public boolean isCompressed() {
		return 0 != (tag & COMPRESSED);
	}

	public void setTrunked() {
		tag |= TRUNKED;
	}

	public void setCompressed() {
		tag |= COMPRESSED;
	}

	public void clearTrunked() {
		tag &= ~TRUNKED;
	}

	public void clearCompressed() {
		tag &= ~COMPRESSED;
	}

	public String toString() {
		return "[SID " + serviceId + " , CID " + commandId + " , SER "
				+ serialId + " , RES " + resCode + " , TAG " + tag + " , KEY "
				+ key + " , LEN " + length + " , FWD " + forward + " DST"
				+ destination + "]";
	}

	public int getLength() {
		Pack p = new Pack();
		p.putMarshallable(this);
		return p.size();
	}
	
	public PacketHeader toNewStyleHeader(boolean hasRes, boolean hasKey) throws PacketHeaderConvertException{
		PacketHeader ph = new PacketHeader();
		//unchanged
		ph.setSerialId(this.serialId);
		//changed
		if(this.serviceId > 255 || this.commandId > 255){
			throw new PacketHeaderConvertException(this);
		}
		ph.setServiceId((byte)this.serviceId);
		ph.setCommandId((byte)this.commandId);
//		//flags
		if(this.isCompressed()){
			ph.setCompressed();
		}
		if(this.isTrunked()){
			ph.setTrunked();
		}
		
		//variable header
		if(hasRes){
			ph.setRescodeFlag();
			if(this.resCode > Short.MAX_VALUE){
				throw new PacketHeaderConvertException(this);
			}
			ph.setResCode((short)this.resCode);
		}
		if (hasKey) {
			ph.setKeyFlag();
			ph.setKey(this.key);
		}
		return ph;
	}
}