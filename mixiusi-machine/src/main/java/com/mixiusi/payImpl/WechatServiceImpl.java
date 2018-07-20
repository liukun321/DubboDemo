package com.mixiusi.payImpl;

import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.inter.Pay;
import com.mixiusi.service.PayStrategyService;
import com.mixiusi.utils.WechatPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * 微信支付实现类
 * @author liukun
 *
 */
@Pay(2)
@Component
public class WechatServiceImpl implements PayStrategyService {
	private final Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);
	private static String WECHAT_SUCCESS = "SUCCESS";
	private static String USERPAYING = "USERPAYING";
	private static String SYSTEMERROR ="SYSTEMERROR";
	@Override
	public Boolean orderPay(Integer payMethod, String auth_code, Double reduce_price, Payindent payindent, String ip) {
		Boolean tradeStatus = false;
		String indent_id = payindent.getIndentId();
		String value = StringUtils.convertDoubleToStr(reduce_price);
		try {
			Map<String, String> resultMap = WechatPayUtils.payByWechat(indent_id, auth_code,
					value, ip);//Double.toString(reduce_price*100).replaceAll(".", "")
			String result_code = resultMap.get("result_code");
			String return_code = resultMap.get("return_code");
			String err_code = resultMap.get("err_code");
			String transaction_id = resultMap.get("transaction_id");//获取微信支付订单号
			payindent.setOrderId(transaction_id);
			log.info("return_code= {} --err_code = {} --result_code= {}", return_code , err_code ,result_code);
			//
			if (WECHAT_SUCCESS.equals(return_code)){//请求成功
				//返回给APP，订单是待支付状态
				log.info("----------------微信支付请求成功----------------{}", resultMap.toString());
				if (WECHAT_SUCCESS.equals(result_code)) {//支付成功
					log.info("----------------微信支付扣款成功----------------");
					tradeStatus = true;
				}
				if (USERPAYING.equals(err_code)){
					//用户正在支付中，等待10秒钟后再查询订单详情
					String query_result = "";
					for(int i=0; i < 3; i++) {
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
					if(!WECHAT_SUCCESS.equals(query_result)) {
						log.info("----------------微信支付扣款成功失败----------------");
					}
					//超时后系统会自动取消订单？？？
				}
				if(SYSTEMERROR.equals(err_code)) {//NOTENOUGH：余额不足
					//系统错误则，查询订单，返回订单的实际结果
					Thread.sleep(5000);
					Map<String, String> queryMap = WechatPayUtils.queryOrder(indent_id);
					String queryCode = queryMap.get("result_code");
					String query_result = queryMap.get("return_code");
					if(WECHAT_SUCCESS.equals(queryCode) && WECHAT_SUCCESS.equals(query_result)) {
						tradeStatus = true;
					}
				}
			}
		} catch (Exception e) {
			log.error("微信支付过程异常：" + e.getMessage());
		}
		return tradeStatus;
	}

}
