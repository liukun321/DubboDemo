package com.mixiusi.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixiusi.bean.Payindent;
import com.mixiusi.service.PayStrategyService;

/**
 * 执行支付上下文
 * @author liukun
 *
 */
@Component
public class PayContext {
	private PayStrategyService payStrategyService;
	public Boolean orderPay(Integer payMethod, String auth_code, Double reduce_price,
			Payindent payindent, String ip) throws Exception {
		payStrategyService = PayStrategyFactory.getInstance().creator(payMethod);
		System.out.println("22222222222222222222222222222222"+payStrategyService);
		return payStrategyService.orderPay(payMethod, auth_code, reduce_price, payindent, ip);
	}

}
