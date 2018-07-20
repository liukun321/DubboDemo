package com.mixiusi.handler;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mixiusi.protocol.request.Request;
import com.mixiusi.server.*;

//��������
public abstract class RequestHandler {
//	protected Database database = Database.sharedInstance();

	abstract public void processRequest(Request request, ChannelHandlerContext ctx);

}
