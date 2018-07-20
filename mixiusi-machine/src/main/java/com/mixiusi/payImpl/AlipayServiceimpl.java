package com.mixiusi.payImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.mixiusi.bean.Payindent;
import com.mixiusi.inter.Pay;
import com.mixiusi.service.PayStrategyService;
import com.mixiusi.utils.AlipayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Pay(1)
@Component
public class AlipayServiceimpl implements PayStrategyService {
	private final Logger log = LoggerFactory.getLogger(AlipayServiceimpl.class);
	private static String ALIPAY_SUCCESS = "10000";
	@Override
	public Boolean orderPay(Integer payMethod, String auth_code, Double reduce_price, Payindent payindent, String ip) {
		//支付宝支付
		Boolean tradeStatus = false;
		String indent_id = payindent.getIndentId();
		String tradeNo = "";
		try {
			AlipayTradePayResponse alipayTradePayResponse = AlipayUtils.alipayByBarCode(indent_id, auth_code, reduce_price);
			tradeNo = alipayTradePayResponse.getTradeNo();//支付宝返回的交易号
			payindent.setOrderId(tradeNo);
			//支付成功
			log.info("支付宝返回的交易号:" + tradeNo);
			if (ALIPAY_SUCCESS.equals(alipayTradePayResponse.getCode())) {
				//更新支付状态
				tradeStatus = true;
			}
		} catch (AlipayApiException e) {
			log.error("支付异常，取消订单：{}", e.getErrMsg());
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
	}

}
