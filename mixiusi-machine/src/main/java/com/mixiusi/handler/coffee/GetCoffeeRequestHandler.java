package com.mixiusi.handler.coffee;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.marshal.Property;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.response.coffee.GetCoffeeResponse;
import com.mixiusi.service.CoffeeInfoService;

/**
 * 2 获取所有品种的咖啡信息
 * @author liukun
 *点击咖啡机的首页后进入咖啡产品的列表页面
 */
@Component
public class GetCoffeeRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetCoffeeRequestHandler.class);
	@Reference
	private CoffeeInfoService service;
	private static CoffeeInfoService coffeeInfoService;
	@PostConstruct
	public void init() {
		coffeeInfoService = service;
	}
	
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("----------查询咖啡的所有产信息--------------------");
		GetCoffeeResponse getCoffeeresponse = new GetCoffeeResponse(request.getLinkFrame().key);
		List<CoffeeInfo> coffeeInfoList = coffeeInfoService.queryAllCoffeeInfo();
		if(null != coffeeInfoList && !coffeeInfoList.isEmpty()) {
			getCoffeeresponse.setarraylen(coffeeInfoList.size());
			log.info(coffeeInfoList.toString());
			for (int i = 0; i < coffeeInfoList.size(); i++) {
				Property property = new Property();
				property.put(1, coffeeInfoList.get(i).getCoffeeId());
				property.put(2, coffeeInfoList.get(i).getCoffeeName());
				property.put(3, Double.toString(coffeeInfoList.get(i).getPrice()));
				property.put(4, coffeeInfoList.get(i).getImgurl());
				log.info("图片地址：" + coffeeInfoList.get(i).getImgurl());
				//咖啡的糖度 信息
				property.put(5, Boolean.toString(coffeeInfoList.get(i).getIsSugar()));
				property.put(6, Boolean.toString(coffeeInfoList.get(i).getIs_new()));
				property.put(7, Boolean.toString(coffeeInfoList.get(i).getDiscount()));
				property.put(8, Double.toString(coffeeInfoList.get(i).getDiscount_price()));
				getCoffeeresponse.getCoffeeInfos().list.add(property);
				
			}
			//获取咖啡机的折扣信息
//			DiscountInfo result = discountInfoService.queryDiscountInfo(request.getLinkFrame().key);
//			JSONObject jsonObj = new JSONObject();
//			if(null != result) {
//				jsonObj.put("discount", result.getDiscount());
//			}else {
//				jsonObj.put("discount", null);
//			}
//			getCoffeeresponse.setFavorable(jsonObj.toString());
			
			getCoffeeresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		}
		ctx.getChannel().write(getCoffeeresponse);
	}

}
