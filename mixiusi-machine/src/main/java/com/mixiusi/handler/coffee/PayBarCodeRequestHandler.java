package com.mixiusi.handler.coffee;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.utils.DateUtils;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.PayBarCodeRequest;
import com.mixiusi.protocol.response.coffee.PayBarCodeResponse;
import com.mixiusi.server.PayContext;
import com.mixiusi.service.CoffeeInfoService;
import com.mixiusi.service.CouponsService;
import com.mixiusi.service.PayindentService;
/**
 * 3 单杯购买订单
 * @author liukun
 *	在咖啡机上选定好咖啡的品种后，点击下单并扫描条形码后向后端发情支付的请求
 *  生成订单，同时调用 第三方接口进行支付
 *  调用成功后，给APP端反馈，之后在请求订单状态查询
 *  
 *  订单取消失败怎么处理
 */
@Component
public class PayBarCodeRequestHandler extends RequestHandler {
	//日志记录
	private final Logger log = LoggerFactory.getLogger(PayBarCodeRequestHandler.class);
	//支付类型
	private static Integer ALIPAY = 1;
	private static Integer WECHAT = 2;
	//优惠券类型
	private static Integer DISCOUNT = 1;
	private static Integer REDUCE = 2;
	private static Integer GIVING = 3;
	/**
	 * 引入service实例
	 */
	@Reference
	private CoffeeInfoService service;
	@Reference
	private CouponsService cservice;
	@Reference
	private PayindentService pservice;
	private static CoffeeInfoService coffeeInfoService;
	private static PayindentService payindentService;
	private static CouponsService couponsService;
	@PostConstruct
	public void init() {
		coffeeInfoService = service;
		payindentService = pservice;
		couponsService = cservice;
	}
		
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("-----------------订单支付请求-----------");
		boolean flag = true;//用于标识付款过程中的异常情况，终止支付请求
		PayBarCodeRequest payBarCodeRequest = (PayBarCodeRequest) request;
		
		boolean tradeStatus = false;
		log.info("----开始支付----");
		String couponsCode = payBarCodeRequest.getCouponsCode();
		String coffeeid = payBarCodeRequest.getCoffeeId();
		String ip = payBarCodeRequest.getIp();
		String auth_code = payBarCodeRequest.getAuthCode();
		String venderName = payBarCodeRequest.getMachineId();
		boolean isHot = payBarCodeRequest.isHot();
		int sugar = payBarCodeRequest.getSugar();
		int coffeeType = payBarCodeRequest.getCoffeeType();
		int payMethod = 0;
		//咖啡原价/折后价/实际价
		Double price = 0.0, discount_price = 0.0, reduce_price = 0.0;
		//订单Id/优惠券的值
		String indent_id = null, value = null;
		Integer couponsType = null;
		
		//1 判断咖啡是否存在
		CoffeeInfo coffeeInfo = coffeeInfoService.queryCoffeeInfoById(coffeeid);
		//2 查询优惠券
		Coupons coupons = couponsService.queryCouponsByCode(couponsCode);
		//3 根据auth_code判断支付方式
		payMethod = payMethod(auth_code, payMethod);
		
		/**
		 * 1 如果咖啡不存在，则全部终止
		 * 2咖啡存在，优惠券不存在和存在，价钱处理方式不同
		 * 
		 * 	有优惠券： 1 打折 实际价钱x折扣
		 * 		   2 减免 实际价-优惠
		 *         3赠送 实际价 = 0.0
		 *  无优惠券：实际价 = 咖啡折后价
		 */
		if(null != coffeeInfo) {
			price = coffeeInfo.getPrice();//咖啡原价
			discount_price = coffeeInfo.getDiscount_price();//折后价
			if(null != coupons) {
				couponsType = coupons.getType();
				value = coupons.getValue();
			}
			if(GIVING == couponsType) {//直接送一杯咖啡
				//生成订单，支付成功
				discount_price = 0.0;
				price = 0.0;
				reduce_price = 0.0;
				tradeStatus = true;
			}
			if(null != couponsType){
				if(couponsType == REDUCE)
					reduce_price = setDoubleScale(discount_price - Double.valueOf(value));//最终价
				else if(couponsType == DISCOUNT)
					reduce_price = setDoubleScale(discount_price * Double.valueOf(value));
			}else {
				reduce_price = discount_price;
			}
		}else {
			flag = false;
		}
		
		//将订单入库
		Payindent payindent = null;
		if(flag) {
			//生成订单的ID  咖啡机Id + 咖啡Id + 时间戳
			indent_id = venderName + coffeeid + DateUtils.dateFormat("yyyyMMddHHmmss");
			log.info("订单Id = " + indent_id);
			payindent = new Payindent();
			payindent.setIndentId(indent_id);
			payindent.setMachineId(venderName);
			payindent.setCoffeeId(coffeeid);
			payindent.setPrice(reduce_price);//实际价钱
			payindent.setDiscount_price(discount_price);//折后价
			payindent.setPriceOri(price);//原始价
			payindent.setPayMethod(payMethod);
			payindent.setPayStatus(0);
			payindent.setCreateTime(new Date());
			payindent.setIsHot(isHot);
			payindent.setSugar(sugar);
			payindent.setCoffeeType(coffeeType);
			payindent = payindentService.addPayindent(payindent);
		}
		//设置返回的值
		PayBarCodeResponse payBarCoderesponse = new PayBarCodeResponse(request.getLinkFrame().key);
		payBarCoderesponse.setCoffeeId(coffeeid);
		payBarCoderesponse.setPayIndent(indent_id);
		payBarCoderesponse.setPrice(Double.toString(reduce_price));
// 		if (ALIPAY == payMethod && flag) {
//			tradeStatus = alipayMethod(tradeStatus, auth_code, reduce_price, indent_id, payindent);
//		}else if (WECHAT == payMethod && flag) {
//			tradeStatus = wechatPayMethod(tradeStatus, ip, auth_code, reduce_price, indent_id, payindent);
//		}
		
		if(flag) {
			PayContext context = new PayContext();
			try {
				// TODO 参数封装 限制方法参数的数量
				tradeStatus = context.orderPay(payMethod, auth_code, reduce_price, payindent, ip);
			} catch (Exception e) {
				log.error(e.getMessage());
				tradeStatus = false;
			}
		}
		log.info("----22222----" + tradeStatus);
		if(tradeStatus) {
			paySuccess(payindent, payBarCoderesponse, coupons);
		}else {
			payFailure(payindent, payBarCoderesponse);
		}
		ctx.getChannel().write(payBarCoderesponse);//反馈给APP
	}
	/**微信支付方式
	 * 
	 * @param tradeStatus
	 * @param ip
	 * @param auth_code
	 * @param reduce_price
	 * @param indent_id
	 * @param payindent
	 * @return
	 */
	/*private boolean wechatPayMethod(boolean tradeStatus, String ip, String auth_code, Double reduce_price,
			String indent_id, Payindent payindent) {
		String value = StringUtils.convertDoubleToStr(reduce_price);
		try {
			Map<String, String> resultMap = WechatPayUtils.payByWechat(indent_id, auth_code,
					value, ip);//Double.toString(reduce_price*100).replaceAll(".", "")
			String result_code = resultMap.get("result_code");
			String return_code = resultMap.get("return_code");
			String err_code = resultMap.get("err_code");
			String transaction_id = resultMap.get("transaction_id");//获取微信支付订单号
			payindent.setOrderId(transaction_id);
			log.info("return_code=" + return_code + "--err_code = " + err_code + "--result_code= " + result_code);
			//
			if ("SUCCESS".equals(return_code)){//请求成功
				//返回给APP，订单是待支付状态
				log.info("----------------微信支付请求成功----------------" + resultMap.toString());
				if ("SUCCESS".equals(result_code)) {//支付成功
					log.info("----------------微信支付扣款成功----------------");
					tradeStatus = true;
				}
				if ("USERPAYING".equals(err_code)){
					//用户正在支付中，等待10秒钟后再查询订单详情
					String query_result = "";
					for(int i = 0; i < 3; i++) {
						//最多循环三次，30秒超时
						Thread.sleep(10000);
						Map<String, String> queryMap = WechatPayUtils.queryOrder(indent_id);
						String queryCode = queryMap.get("result_code");
						query_result = queryMap.get("return_code");
						if("SUCCESS".equals(queryCode) && "SUCCESS".equals(query_result)) {
							tradeStatus = true;
							break;
						}
					}
					//三次结束之后还是失败
					if(!"SUCCESS".equals(query_result)) {
						log.info("----------------微信支付扣款成功失败----------------");
					}
					//超时后系统会自动取消订单？？？
				}
				if("SYSTEMERROR".equals(err_code)) {//NOTENOUGH：余额不足
					//系统错误则，查询订单，返回订单的实际结果
					Thread.sleep(5000);
					Map<String, String> queryMap = WechatPayUtils.queryOrder(indent_id);
					String queryCode = queryMap.get("result_code");
					String query_result = queryMap.get("return_code");
					if("SUCCESS".equals(queryCode) && "SUCCESS".equals(query_result)) {
						tradeStatus = true;
					}
				}
			}
		} catch (Exception e) {
			log.error("微信支付过程异常：" + e.getMessage());
		}
		return tradeStatus;
	}*/
	/**
	 * 支付宝支付
	 * @param tradeStatus
	 * @param auth_code
	 * @param reduce_price
	 * @param indent_id
	 * @param payindent
	 * @return
	 */
	/*private boolean alipayMethod(boolean tradeStatus, String auth_code, Double reduce_price, String indent_id,
			Payindent payindent) {
		//支付宝支付
		String tradeNo = "";
		try {
			AlipayTradePayResponse alipayTradePayResponse = AlipayUtils.alipayByBarCode(indent_id, auth_code, reduce_price);
			tradeNo = alipayTradePayResponse.getTradeNo();//支付宝返回的交易号
			payindent.setOrderId(tradeNo);
			//支付成功
			log.info("支付宝返回的交易号:" + tradeNo);
			if ("10000".equals(alipayTradePayResponse.getCode())) {
				//更新支付状态
				tradeStatus = true;
			}
		} catch (AlipayApiException e) {
			log.error("支付异常，取消订单：" + e.getErrMsg());
			try {
				AlipayTradeCancelResponse alipayTradeCancelResponse = AlipayUtils.alipayTradeCancel(indent_id, tradeNo);
				if (alipayTradeCancelResponse.isSuccess()) {
					log.info("AlipayTradeCancel--调用成功");
				} else {
					log.info("AlipayTradeCancel--调用失败");
				}
			} catch (AlipayApiException e1) {
				log.error("订单取消异常：" + e1.getErrMsg());
			}
		}
		return tradeStatus;
	}*/
	/**
	 * 判断支付方式
	 * @param auth_code
	 * @param payMethod
	 * @return
	 * @throws NumberFormatException
	 */
	private int payMethod(String auth_code, int payMethod) throws NumberFormatException {
		if(!StringUtils.isNull(auth_code)) {
			String pre = auth_code.substring(0, 2);
			int authPre = Integer.parseInt(pre);
			if(authPre > 9 && authPre < 15)//微信支付
				payMethod = 2;
			if(authPre > 24 && authPre < 31)//支付宝支付
				payMethod = 1;
		}
		return payMethod;
	}
	/**
	 * 支付失败
	 * @param pay
	 * @param payBarCoderesponse
	 * @param payindentService
	 */
	private void payFailure(Payindent pay, PayBarCodeResponse payBarCoderesponse) {
		if(null != pay) {
			pay.setPayStatus(2);
			payindentService.addPayindent(pay);//更新订单状态
		}
		payBarCoderesponse.setStatus("2");
		payBarCoderesponse.getLinkFrame().resCode = 500;
	}
	/**
	 * 支付成功
	 * @param pay
	 * @param payBarCoderesponse
	 * @param payindentService
	 */
	private void paySuccess(Payindent pay, PayBarCodeResponse payBarCoderesponse, Coupons coupons) {
		if(null != pay) {
			pay.setPayStatus(1);
			payindentService.addPayindent(pay);//更新订单状态
		}
		//支付成功将优惠券状态设置为已使用
		if(null != coupons)
		{
			coupons.setIs_use(true);
			couponsService.updateCoupons(coupons);
		}
		payBarCoderesponse.setStatus("1");
		payBarCoderesponse.getLinkFrame().resCode = 200;
	}
	/**
	 * 设置double类型的精度
	 * @param value
	 * @return
	 */
	private Double setDoubleScale(Double value){
		
		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		
	}
}
