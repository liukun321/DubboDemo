package com.mixiusi.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.GetCouponsRequest;
import com.mixiusi.protocol.response.coffee.GetCouponsResponse;
import com.mixiusi.service.CouponsService;

/**
 * 查询优惠券信息
 * @author liukun
 *
 */
@Component
public class GetCouponsRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetCouponsRequestHandler.class);
	@Reference
	private CouponsService dservice;
	private static CouponsService couponsService;
	@PostConstruct
	public void init() {
		couponsService = dservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("------------------查询优惠券信息，验证是否有效-----------------");
		GetCouponsRequest getCouponsRequest = (GetCouponsRequest)request;
		String couponCode = getCouponsRequest.getCouponCode();
		log.info(getCouponsRequest.getCouponCode() + "--" + getCouponsRequest.getLinkFrame().resCode + "===" + request.getLinkFrame().serialId);
		System.out.println(request.getLinkFrame().key);
		GetCouponsResponse getCouponsresponse = new GetCouponsResponse(request.getLinkFrame().key);
		getCouponsresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		JSONObject jsonObj = new JSONObject();
		if(!StringUtils.isNull(couponCode)) {
			Coupons result = couponsService.queryCouponsByCode(couponCode);
			if(null != result) {
				String value = result.getValue();
				Integer type = result.getType();
				jsonObj.put("couponsType", type);
				jsonObj.put("value", value);//1
			}else {
				jsonObj.put("couponsType", 0);
				jsonObj.put("value", null);//1
			}
		}
		getCouponsresponse.setFavorable(jsonObj.toString());
		ctx.getChannel().write(getCouponsresponse);

	}
}
