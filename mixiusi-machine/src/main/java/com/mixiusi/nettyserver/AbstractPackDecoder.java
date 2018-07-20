package com.mixiusi.nettyserver;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.mixiusi.protocol.PacketHeader;
import com.mixiusi.protocol.pack.UnpackException;



public abstract class AbstractPackDecoder extends FrameDecoder implements
		IPackParser {
	
	int packetSize = -1;
	byte[] sizeBytes = new byte[4];

	public AbstractPackDecoder() {
		super(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty
	 * .channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		ArrayList<Object> ret = new ArrayList<Object>();

		while (buffer.readableBytes() > 0) {
			if (packetSize == -1) { 
				buffer.readBytes(sizeBytes);
				//sizeBytes = decrypt(sizeBytes);
				packetSize = PacketHeader.bytesToLength(sizeBytes);
				if (packetSize <= 0)
					throw new UnpackException("Invalid packet, size=" + packetSize);
				packetSize += PacketHeader.getByteCount(packetSize);
			}

			
			
			if (buffer.readableBytes() < packetSize - 4)
				break;

			// 鑷虫锛岄�氶亾缂撳啿鍖轰腑宸茬粡鍖呭惈瀹屾暣鐨勬暟鎹寘
			// 灏嗛�氶亾缂撳啿鍖轰腑鐨勬暟鎹寘澶嶅埗鍑烘潵
			byte[] data = new byte[packetSize - 4];
			buffer.readBytes(data, 0, packetSize - 4);
			//data = decrypt(data);
			byte[] packet = new byte[packetSize];
			System.arraycopy(sizeBytes, 0, packet, 0, 4);
			System.arraycopy(data, 0, packet, 4, packetSize-4);
			packetSize = -1;
			try {
				Object o = parsePacket(ctx, packet);
				if (o != null) {
					ret.add(o);
				} else {
					continue;
				}
			} catch (UnpackException ex) {
				
			}
		}
		if (ret.size() > 0) {
			return ret;
		}
		return null;
	}

	private int convertirOctetEnEntier(byte[] b){    
	    int MASK = 0xFF;
	    int result = 0;   
	        result = b[0] & MASK;
	        result = result + ((b[1] & MASK) << 8);
	        result = result + ((b[2] & MASK) << 16);
	        result = result + ((b[3] & MASK) << 24);            
	    return result;
	}
	
	/**
	 * 杩斿洖瀵逛紶鍏ョ殑瀛楄妭鏁扮粍鐨勮В鐮佺粨鏋滐紝杩欎釜缁撴灉浼氫綔涓哄悗缁笟鍔″鐞嗙殑杈撳叆鍙傛暟銆傝瑙丄bstractChannelHandler鍙婂叾瀛愮被
	 * 
	 * @param ctx
	 * @param packet
	 * @return
	 * @throws Exception
	 */
	public abstract Object parsePacket(ChannelHandlerContext ctx, byte[] packet)
			throws Exception;
	
	/**
	 * 杩斿洖瀵逛紶鍏ョ殑瀛楄妭瑙ｅ瘑鍚庣殑缁撴灉锛屽鏋滄槸娌℃湁鍔犲瘑鐨勫寘锛岀洿鎺ヨ繑鍥炲師鏁版嵁鍗冲彲
	 */
	public abstract byte[] decrypt(byte[] src);
}
