package com.mixiusi.protocol;

public class PacketHeaderConvertException extends Exception {

	private static final long serialVersionUID = 315826917795890681L;
	private LinkFrame lf;
	
	public PacketHeaderConvertException( LinkFrame lf) {
		this.lf = lf;
	}

	@Override
	public String getMessage() {
		return lf.toString();
	}

}
