package com.mixiusi.handler.coffee;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.response.coffee.GetAdvPicResponse;

/**
 * 1 获取咖机首页图片
 * @author liukun
 *	咖啡机上的第一步，获取首页图片
 */
public class GetAdvPicsRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetAdvPicsRequestHandler.class);
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("1111---获取首页图片");
		// TODO Auto-generated method stub
		// String advurl = " ";
		JSONArray advurls = new JSONArray();
		advurls.add(
				"http://m.qpic.cn/psb?/V11PUUCS0IrXcc/wp5mGNmL*Z*7obxrAEUcR3PUPzjHKu4*O4IDo4gWXMQ!/b/dGcBAAAAAAAA&bo=9AFTAQAAAAARF4c!&rf=viewer_4");
		String advurl = advurls.toString();
		GetAdvPicResponse getAdvPicresponse = new GetAdvPicResponse(request.getLinkFrame().key);
		getAdvPicresponse.setAdvPics(advurl);
		getAdvPicresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(getAdvPicresponse);

	}
}
