package com.mixiusi.nettyserver;

import org.jboss.netty.channel.ChannelHandlerContext;

public interface IPackParser 
{
	public Object parsePacket(ChannelHandlerContext ctx, byte[] packet) throws Exception ;
	public abstract byte[] decrypt(byte[] src);
}
