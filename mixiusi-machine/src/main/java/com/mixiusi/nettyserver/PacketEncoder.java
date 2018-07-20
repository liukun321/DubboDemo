package com.mixiusi.nettyserver;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.mixiusi.protocol.pack.Pack;
import com.mixiusi.protocol.pack.PackagePacker;
import com.mixiusi.protocol.response.Response;

public class PacketEncoder extends OneToOneEncoder {
	
	PackagePacker packer;
	
	public PacketEncoder() {
	}
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		// TODO Auto-generated method stub
		
		if(msg instanceof Response)
		{
			Response req = (Response)msg;
			
			Pack p = packer.packRequest(req);
			return p;
		}
		else
		{
			return msg;
		}
	}

	public void setPacker(PackagePacker packer) {
		this.packer = packer;
	}
	
	public void reset() {
		
	}
}
