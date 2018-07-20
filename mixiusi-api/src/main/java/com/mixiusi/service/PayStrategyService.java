package com.mixiusi.service;

import com.mixiusi.bean.Payindent;

/**
 * 支付接口
 * @author liukun
 *
 */
public interface PayStrategyService {
	/**
	 * 
	 * @param payMethod 支付方式
	 * @param auth_code 支付码信息
	 * @param reduce_price 支付价钱
	 * @param payindent 订单信息
	 * @param ip 机器Ip
	 * @return
	 */
	public Boolean orderPay(Integer payMethod, String auth_code, Double reduce_price,
			Payindent payindent, String ip);
}
