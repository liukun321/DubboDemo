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
import com.mixiusi.bean.CoffeeList;
import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.Pickup;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.marshal.Property;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.PickupRequest;
import com.mixiusi.protocol.response.coffee.PickupResponse;
import com.mixiusi.service.CoffeeInfoService;
import com.mixiusi.service.MobileCoffeelistService;
import com.mixiusi.service.MobilePayService;
import com.mixiusi.service.PickupService;
/**
 * 取货请求
 * @author liukun
 *
 */
@Component
public class PickupRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(PickupRequestHandler.class);
	@Reference
	private PickupService pservice;
	private static PickupService pickupService;
	@Reference
	private CoffeeInfoService service;
	private static CoffeeInfoService coffeeInfoService;
	@Reference
	private MobilePayService mservice;
	private static MobilePayService mobilePayService;
	
	@Reference
	private MobileCoffeelistService mcservice;
	private static MobileCoffeelistService mobileCoffeelistService;
	@PostConstruct
	public void init() {
		pickupService = pservice;
		mobilePayService = mservice;
		coffeeInfoService = service;
		mobileCoffeelistService = mcservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("------------开始取货请求-----------");
		PickupRequest pickupRequest = (PickupRequest)request;
		PickupResponse pickupResponse = new PickupResponse(request.getLinkFrame().key);
		String pickupCode = pickupRequest.getPickupCode();
		log.info("---------------" + pickupCode + "---------------" + pickupService);
		Pickup pk = pickupService.queryPickup(pickupCode);
		MobilePay mpay = null;
		List<MobileCoffeeList> mp = mobileCoffeelistService.queryCoffeeAll(pk.getIndentId());
		if(null != mp && !mp.isEmpty()) {
			
			mpay = mp.get(0).getMid();
			pickupResponse.setarraylen(mp.size());
			/**
			 * coffeeId/coffeeeName/coffeeType/isHot/sugar/  
			 */
			for (MobileCoffeeList mc : mp) {
				CoffeeList cl = mc.getCid();
				//同一杯的数量大于1时，需要循环
				for(int j = 0; j < cl.getCupNum(); j++) {
					CoffeeInfo ci = coffeeInfoService.queryCoffeeInfoById(cl.getCoffeeId());
					if(null == ci) {//发现订单中的咖啡在数据库中没有则这条数据跳过，继续下一杯数据的传输
						continue;
					}
					Property property = new Property();
					property.put(1, cl.getCoffeeId());
					log.info(ci.getCoffeeName() + "---" + cl.toString());
					property.put(2, ci.getCoffeeName());
					property.put(3, Integer.toString(cl.getCoffeeType()));
					//咖啡的糖度 信息
					property.put(4, Boolean.toString(cl.getIsHot()));
					property.put(5, Integer.toString(cl.getSugar()));
					pickupResponse.getCoffeeInfos().list.add(property);
				}
			}
			
			//TODO 更新两张表中的数据，能否保证在同一个事务中
			
			//更新取货码的使用状态
			pk.setIsUse(true);
			pickupService.updateStatus(pk);
			//更新微信端的订单取货状态
			mpay.setOver(true);
			mobilePayService.add(mpay);
		}else {
			pickupResponse.getLinkFrame().resCode = 500;
		}
		pickupResponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(pickupResponse);
	}

}
