package com.mixiusi.utils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.mixiusi.bean.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *微信支付 工具类
 * @author liukun
 *
 */
public class WechatPayUtils {
	private final static Logger log = LoggerFactory.getLogger(WechatPayUtils.class);
	private static final String APP_ID = "wwace37b37861f586c";
//	private static final String SIGN = "C380BEC2BFD727A4B6845133519F3AD6";
	/**
	 * 微信刷卡支付
	 * @param indent_id
	 * @param authCode
	 * @param out_trade_no
	 * @param total_fee
	 * @param spbill_create_ip
	 * @return
	 * @throws Exception 
	 */
	 
	public static Map<String, String> payByWechat(String indent_id, String authCode, String total_fee, String spbill_create_ip) throws Exception {
		
		Map<String, String> wxpresult = null;
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
		String wxindent_id = cut(indent_id);
		Map<String, String> data = new HashMap<>();
		data.put("out_trade_no", wxindent_id);//商户系统内部订单号
		data.put("auth_code", authCode);//条形码信息
		data.put("spbill_create_ip", spbill_create_ip);//调用微信支付API的机器IP
		data.put("total_fee", total_fee);//单位为分
		data.put("body", "刷卡支付测试");
		
		//将config中的参数加载到data中
		wxpay.fillRequestData(data);
		//刷卡支付请求
		wxpresult = wxpay.microPay(data);
		return wxpresult;
	}
	/**
	 * 微信订单查询
	 * @param indent_id
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> queryOrder(String indent_id) throws Exception {
		Map<String, String> resultMap = null;
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
//		String wxindent_id = cut(indent_id);
		Map<String, String> data = new HashMap<>();
		data.put("out_trade_no", indent_id);//商户系统内部订单号
		
		//将config中的参数加载到data中
		wxpay.fillRequestData(data);
		//刷卡支付请求
		resultMap = wxpay.orderQuery(data);
		return resultMap;
	}
	
	/**
	 * 微信订单查询
	 * @param indent_id
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> cancelOrder(String indent_id, String transaction_id) throws Exception {
		Map<String, String> resultMap = null;
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
		String wxindent_id = cut(indent_id);
		Map<String, String> data = new HashMap<>();
		data.put("out_trade_no", wxindent_id);//商户系统内部订单号
		data.put("transaction_id", transaction_id);
		
		//将config中的参数加载到data中
		wxpay.fillRequestData(data);
		//刷卡支付请求
		resultMap = wxpay.closeOrder(data);
		return resultMap;
	}
	
	/**
	 * 微信退款
	 * @param indent_id
	 * @param out_refund_no 商户退款单号
	 * @param total_fee 订单金额
	 * @param refund_fee 退款金额
	 * @param refund_desc 退款原因
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> refound(String indent_id, String total_fee, String refund_fee, String refund_desc) throws Exception {
		Map<String, String> resultMap = null;
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
//		String wxindent_id = cut(indent_id);
		Map<String, String> data = new HashMap<>();
		String time = DateUtils.dateFormat("yyyyMMddHHmmss");
		data.put("transaction_id", indent_id);//商户系统内部订单号
		data.put("out_refund_no", time);//商户退款单号
		data.put("total_fee", total_fee);//订单金额
		data.put("refund_fee", refund_fee);//退款金额
		data.put("refund_desc", refund_desc);//退款原因
		
		//将config中的参数加载到data中
		wxpay.fillRequestData(data);
		//刷卡支付请求
		resultMap = wxpay.refund(data);
		return resultMap;
	}
	
	//生成订单号
	private static String cut(String indent_id) {
		// TODO Auto-generated method stub
		String newindent_id = "";
		for (int i = 0; i < indent_id.length(); i++) {
			if (indent_id.charAt(i) == ' ' || indent_id.charAt(i) == ':' || indent_id.charAt(i) == '.') {

			} else {
				newindent_id += indent_id.charAt(i);
			}
		}
		return newindent_id;
	}
	
	static class MyConfig implements WXPayConfig {

		// 公众账号ID
		public String getAppID() {
			return APP_ID;
		}

		public int getHttpConnectTimeoutMs() {
			return 8000;
		}

		public int getHttpReadTimeoutMs() {
			return 10000;
		}

		// 商户秘钥
		public String getKey() {
			return "Mxs88888Mxs88888Mxs88888Mxs88888";
		}

		// 商户号
		public String getMchID() {
			return "1503728561";
		}

		@Override
		public InputStream getCertStream() {
			return null;
		}
		
	}
	
	/*public static void main(String[] args) {
		String time = DateUtils.dateFormat("yyyyMMddHHmmss");
		System.out.println(time);
		 try {
//			Map<String, String> map = payByWechat(time, "134628204358343699", "1", "192.168.0.100");
//			Map<String, String> map = queryOrder("20180511164232");
			
			Map<String, String> map = refound("20180511164232", "1", "1", "测试");
			String temp = map.get("return_code");
			System.out.println(temp + "==" + map.toString());
			String result_code = map.get("result_code");
			System.out.println(result_code);
		 
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
	}*/
}
