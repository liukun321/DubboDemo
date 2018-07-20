package com.mixiusi.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.marshal.ArrayMable;
import com.mixiusi.protocol.marshal.Property;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.GetDosingListRequest;
import com.mixiusi.protocol.response.coffee.GetDosingListResponse;
import com.mixiusi.service.MaterialService;

//获取配料信息
@Component
public class GetDosingListRequestHandler extends RequestHandler {
	@Reference
	private MaterialService service;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		materialService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {

		ArrayMable coffeeDosingList = new ArrayMable();

		GetDosingListRequest getDosingListRequest = (GetDosingListRequest) request;
		System.out.println(getDosingListRequest.getLinkFrame().key);

//		int flag = database.insertmaterial(getDosingListRequest.getLinkFrame().key);
//		materialService.updateMaterial(material);

		/* if(getDosingListRequest.getLinkFrame().key.equals("lutao")){ */
		GetDosingListResponse getDosingListresponse = new GetDosingListResponse(
				getDosingListRequest.getLinkFrame().key);
		getDosingListresponse.setlistlen(7);
		for (int i = 0; i < 7; i++) {
			Property property = new Property();
			if (i == 0) {
				property.put(1, "1");
				property.put(2, "water");
				property.put(4, "0");
			} else if (i == 1) {
				property.put(1, "2");
				property.put(2, "cupNum");
				property.put(4, "0");
			} else if (i == 2) {
				property.put(1, "3");
				property.put(2, "milk");
				property.put(4, "1");
			} else if (i == 3) {
				property.put(1, "4");
				property.put(2, "sugar");
				property.put(4, "2");
			} else if (i == 4) {
				property.put(1, "5");
				property.put(2, "chocolate");
				property.put(4, "3");
			} else if (i == 5) {
				property.put(1, "6");
				property.put(2, "milktea");
				property.put(4, "4");
			} else if (i == 6) {
				property.put(1, "7");
				property.put(2, "coffeebean");
				property.put(4, "9");
			}
			getDosingListresponse.getcoffeeDosingList().list.add(property);

		}

		getDosingListresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		/*
		 * if (flag == 0) getDosingListresponse.getLinkFrame().resCode = 0;
		 */

		ctx.getChannel().write(getDosingListresponse);

	}
}
