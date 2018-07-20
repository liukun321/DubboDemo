package com.mixiusi.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付宝支付工具类
 * @author liukun
 *
 */
public class AlipayUtils {
	private final static Logger log = LoggerFactory.getLogger(AlipayUtils.class);
	private static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
	private static final String APP_ID = "2018022802289573";
	private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXP/1UO5/CcmmrSu4SDeOilNVTypiHrdF+QGX2Vkv4gvnvHwXu1utoXXOp/YVVW8XgCHz3oO1b9WDVCMA9kiU2uum4chOkPOAUOS3apvZkv+pUWarNpBGArBwWNSNkRrRnBQFabnylvgWh8BQGftx6X3hl3ejUnmJ19e3TAyOhHMc0i2CSXz34m2RwHoik9nJ20gAhlCzToQNqVRSbHOD2lZlYDGP7e2NoVDQ6HJFxM3u09qGLm34ZijX9j5/NHEwWZSQi3iz7gT2iqc8fRXFIMiLpPrn37nCJm/eIWOo6nxnJzoDWjkghF6xejiMnigGxo5Ari36lB3XfOS8V6reFAgMBAAECggEAEt+nSwWNsAP676cQAiy9hSIxZJ1ZX0TvaWO71Xt7S218prwmT2F+Sq2uqz25j6c+D/C/N19bfyglhy/qXUSJZET6uUty7AG0rJFeCXTgNj5EjXYK/FJG5NmVP6gt3Gt+Q4S6YIB9CWmm5khBF3ZpKNQVABEq5q1E+BdSMAyqO4ZcfjoC6hb7sIlRvhO9JOYfInGxOL55sZRhi87oG7xoSoE7xsZ2mKigG3kkjMV/4ziVfxA4cgQmVZ9EkMneTTvQi4kqCXrPDaAdrsgl16l6WhNWHdJ5EPSo04hv9u+oBAEfsddCJvYff+1eEeYzu/peV2ua9eP/1aJVDwwOqFWFgQKBgQDXVB0Vdd0YxCItjXCigQWUewTM/gCnXdK8XTkZuO5IUm29dYiYPQTBkMwRPxL8xhHYIv9CvjnLiuJkMWcBNnF0Hamr0upb16Dmz1/dnk3I5FxZQZu6LJlOzY/D6YEAa+Lfj3VB91I8A4Clv9e5YlXBnoW8abS3gfbH+DI6pk1ZcQKBgQCz0XO0Wb3BDH+neMO16b1HJUi72+aWTLu17BnqpfAdFs3SPjoAfJAG0uweiceii3AAHUaVY16OYKbf4d6lgOBhKfNUKvzg10V4muKfTocHAMz0QQFh+tweiRvOd5nHAjmjgLBlDi5icvVv8qj20XjLgc6uFWOm9+x4hKL7D2HVVQKBgDbzo1x3sM4CN8qyPt1p2dezsVzzMY2E1yP1En5rAHx6dMEV/p8Da3ROlJWOKDVUAvfKrqQE0dENB4uUQ/o+P6PncgaElASOOeTNZWS0YptzE9I/eROBEDrZIOhZbe+CmOp+vOjxyg3AwxMJq28HFmWMJAsaWuE+DJGp2H+5MyIxAoGBALPAmCJcZ1SY0u8tyK6LtrttKPUqEKqEkEx+dTXcpVfe2Obnb4HXAv7fUEwCvT7elpp2qX1idT5sncRF/RSC7UoT7ntf8aQtbfPvGMXZcR9uDHPKm8A/TlV0CAjwBBgtQEMSvMJ4V6PweStsbr00jsUYwOvC0/gv9AJRzL6eFNV1AoGAdMBi/JRird3JYHZtX31Q0dwTaNU6ObrbLlDVBCINZ77XXYSg9gBMSBy/FNGU1OphG7Ojb4S4hfQamnNL17YTnqipxVpMPdiGrDLlh9AvlIkjGrrwEBlHHLAJAAUaT90IhJnP/rBHgbcix0xEI1kpKEfqoe2MiH6XZQ9C16GtKJ0=";
	private static final String CHARSET = "utf-8";
	private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqUnBQDVeBdV47W2BGzpy0kiMXb1ahYEXWSLUs+r/XmCLc9mEXVUfA8/3Ob/NfdopW/BDCqvNiSnIWx2wj9+7gqXnzAtn3C77govHv5vHU5EU1pUB67VVJk2mMx2SngVmezryNg8OOa4E1mVLT+AfJkO2q93mL5YU2RtldTq5iblvOb+yq+sGLSh0Qbi2a+nD2U+NFpeROAcH2p5mMY/rYWQPyyxIWX+xEF2Ap8gzvvxCYUP3QCs0sIiUyNfcNlSk7w+n2ViO3ZgHkXfOZnSNvV/L2fLHOzjbFasl7IChl+DA5Q2oYi237iOhVi2siKPvjsblyzjzaYar8D98kFRniQIDAQAB";
	private static final String SHOWURL = "192.168.0.101:8888";
	
//	private static final String GATEWAY_DEV = "https://openapi.alipaydev.com/gateway.do";
//	private static final String APP_ID_DEV = "2016091500513832";
//	private static final String ALIPAY_PUBLIC_KEY_DEV = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3qKqT7NsjY00BWoxrpvnYQajoNLf/JFqWJe3Xum4QxVPm3HU56KPRp3gd+YV8ZIg5hqkaUKKfDHqOyHO6gnoUTdGUjY4ISdv8e/Mk1svJEKLUW5zfaMJ99w4o0DDZ7tqxZZGb0Sy3Qw21mTu9j2k8HFqzvf9/p6K8l4Ive7uRpr7X6wbPGI7awaA/rdLXCQjxBKYKa3LrWJCUDCalkcrprOBcQgKMC9e4nOX3j4mqCEt4YlmzRp8SHH+a1DhKD+gWlWrg4lETmTB8BvF5uT3N0Qxwi8e1nSv5Za4x4stWMCBRwdG4vQ6hRtbxI93CnveQXlQuE959RQFb1Q8nI7tGwIDAQAB";
//	private static final String APP_PRIVATE_KEY_DEV = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTf4heoSLt7ZU76ZCYQ4p1JJwGm0CV6N9KI6x9svgzchTUNHvE/ltAA6qh5uyJhptt7owEHA75GSY39s/75qJKOo3cYv2swF/90TpThlDmAEPBPvAlS+X6sR5NCBozHma9DX3rKWQSkdvzc7vB8cmSXjX2kCMW/dcL2lhkfMF5Q0YqDSQmT45d3d/9yuwXc7qgkoGnAjKOnoSwIN/W4ba7hOeqHTvVaUP4fWblprJcsgKLCocZ0+cTehmC7FW9Ykr2QqoZoI0r3wacwCI8W78vBwt0RNVhpRV4xOLcWOOWd3vKRE1rWtXMUh7YHiZ5+rq3p/s0g3nXDCtWi9ZnU0CdAgMBAAECggEBAIYBek4vK+Lq1YjnPP15b5PaPP8rklQgjK15F7/kF+f1n8LkJAbn6NOSATay7PhNmW0ZK5CYRWo/VR/HV1LPSWcTJ8uxDDQlW5DoxIsaHum4F9a8bcYU3M8UmCj4UYVb+4+LcHl003yjlCftYApmt/IJMhHfBmJfIT+dkm1JgyW9fSZel4sfTXaUgwYOkGtKfwHbtFeA+bBl5hG+pJ3uSABhgAN9JyKmWqvovFqQDGJeZqQkfhy+p8HN9WrDVHqvxZPccCu1sqFVfUDcbe0QI0rxNzsOJmyyWJejlEK4eak/xFaEoU7KQgF+ETrCgKGcgnNwjSkLeD39Y2tSJmUvjukCgYEA0bAvfMVrqDseW9cop4VlB59HvfO3vS40ns8yWx2q4y6l25p2Fwoy8Be+U+MQK0XfCGN8tymudGjuUCGIDfGIUVKLlah4HTUqTJTAhBTk1O6NQbdH+ig4dlj5Tf1/wrXWJdulAscH2nKbwrtClsr8baqfg6rDARgVKmCLDFsmotsCgYEAtBMb9lx74h7aVo6FEoIIme3NwHHbCVeBxEZ/7O6ViSYtB40QW6oo81g/tITeij5BcZ4g//DZHNiyrK68e74b9mhqpLk5/ISfdXqOLUB4VRSyOOv5pH8vOCkBBkomzSIdJTwjRyc4NbZ15hjM00Msrv5Q4FXjg/qBd4moaziP9+cCgYALXWE1gf2zwICEucSaT8CKCTf6M5thxX9UnCY+if0ZEw4qONTtqr/YA3FtWoEl10OfUMURqFCsPoOsFCUrnEunHLI0Ck14R8ljHSzR+8/rOoKtbSGZjVSCFZBtia/C/df9dUocPG1QiaWniD6zrYzA5N6AZGDtTkDXayQeJ1KQwQKBgARQWOEkAgu+tXhRXbq9xrr5SW6My7SDliY/khz48jgg4/9ET9oCT4zU1mHatQIWz7aLFTtMZ8cbIdylKvrD6SOUCMn9bfgwrSlBUFqhKsDPCB34+TgJm/X6dX3TFO7hHFWiBS5VMDGJN3XVrkJ5X36ptQJKzHKC88sLTy+SYVCNAoGAGgeSLMTLftif/hDeYrodf7Uc7RossFawUMZfIOPimYmpG4+VPIVjYLPwB3ZB2BnisWfW8S0qmbQ+Ukdaig57Sc0am1Ms1xpdW2ocpptxc6AVJQLWc7MeAWwm0Kmb989F11QMUL5SB8h5X38bvdfWPt5dHqCowTRt1AIrASP3ebg=";
	
	/**
	 * 支付宝支付，通过扫描用户的条形码进行交易
	 * out_trade_no 	商户订单号，需要保证不重复
	 * scene 	条码支付固定传入bar_code
	 * auth_code 	用户付款码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
	 * subject 	订单标题
	 * store_id 	商户门店编号
	 * total_amount 	订单金额
	 * timeout_express 	交易超时时间
	 * 
	 * 
	 * @return
	 * @throws AlipayApiException 
	 */
	public static AlipayTradePayResponse alipayByBarCode(String indent_id, String auth_code, Double totalprice) throws AlipayApiException{ 
		AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradePayRequest request = new AlipayTradePayRequest(); //创建API对应的request类
		request.setBizContent("{" + ""
				+ "    \"out_trade_no\":" + "\"" + indent_id + "\"" + ","
				+ "    \"scene\":\"bar_code\","
				+ "    \"auth_code\":" + "\"" + auth_code + "\"" + ","
				+ "    \"subject\":\"MixiusiCoffee\","
				+ "    \"store_id\":\"904\","
				+ "    \"timeout_express\":\"90m\","
				+ "    \"total_amount\":" + "\"" + totalprice + "\""
				+ "  }"); //设置业务参数
		AlipayTradePayResponse response = alipayClient.execute(request); //通过alipayClient调用API，获得对应的response类
		log.info(response.getBody() + "---支付宝返回的交易号：" + response.getTradeNo());//trade_no输出的主要是交易号
		return response;
	}
	/**
	 * 支付宝支付交易查询
	 * @param indent_id
	 * @param trade_no
	 * @return
	 * @throws AlipayApiException 
	 */
	public static AlipayTradeQueryResponse alipayTradeQuery(String indent_id, String trade_no) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
		request.setBizContent("{" + ""
				+ "    \"out_trade_no\":" + "\"" + indent_id + "\"" + ","
				+ "    \"trade_no\":" + "\"" + trade_no + "\""
				+ "}"); //设置业务参数
		AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
		log.info(response.getBody() + "---TradeStatus : " + response.getTradeStatus());
		return response;
	}
	/**
	 * 在支付宝交易取消
	 * @param indent_id
	 * @param trade_no
	 * @return
	 * @throws AlipayApiException 
	 */
	public static AlipayTradeCancelResponse alipayTradeCancel(String indent_id, String trade_no) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();//创建API对应的request类
		request.setBizContent("{" + ""
				+ "    \"out_trade_no\":" + "\"" + indent_id + "\"" + ","
				+ "    \"trade_no\":" + "\"" + trade_no + "\""
				+ "}"); //设置业务参数
		AlipayTradeCancelResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
		log.info(response.getBody() + "---TradeNo : " + response.getTradeNo());
		
		return response;
	}
	/**
	 * 支付宝退款
	 * @param indent_id
	 * @param trade_no
	 * @param out_request_no
	 * @param refund_amount
	 * @throws AlipayApiException 
	 */
	public static AlipayTradeRefundResponse alipayTradeRefound(String indent_id, String trade_no, String out_request_no, String refund_amount) throws AlipayApiException {
		AlipayTradeRefundResponse response = null;
		AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY, APP_ID,
				APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
		request.setBizContent("{" + ""	
				+ "    \"out_trade_no\":" + "\"" + indent_id + "\"" + ","
				+ "    \"trade_no\":" + "\"" + trade_no + "\"" + ","
				+ "    \"out_request_no\":" + "\"" + out_request_no + "\"" + ","
				+ "    \"refund_amount\":" + "\"" + refund_amount + "\"" + ","
				+ "\"operator_id\":\"OP001\"," + "\"store_id\":\"NJ_S_001\"," + "\"terminal_id\":\"NJ_T_001\","
				+ "      \"goods_detail\":[{" + "        \"goods_id\":\"natie\","
				+ "\"alipay_goods_id\":\"20010001\"," + "\"goods_name\":\"coffee\"," + "\"quantity\":1,"
				+ "\"price\":" + "\"" + refund_amount + "\"" + "," +
				
			"\"show_url\":" + "\"" + SHOWURL + "\"," + "        }]" + "  }");//设置业务参数
		response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
		log.info(response.getBody() + "----RefundDetailItemList : " + response.getRefundDetailItemList().toString());
		return response;
	}
	
	/*public static void main(String[] args) {
		*//** 支付
		 * 20180508133938
			13:39:38.950 [main] DEBUG sdk.biz.err - 2018-05-08 13:39:38^_^https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018022802289573&biz_content=%7B++++%22out_trade_no%22%3A%2220180508133938%22%2C++++%22scene%22%3A%22bar_code%22%2C++++%22auth_code%22%3A%22285359210267119248%22%2C++++%22subject%22%3A%22MixiusiCoffee%22%2C++++%22store_id%22%3A%22904%22%2C++++%22timeout_express%22%3A%2290m%22%2C++++%22total_amount%22%3A%220.01%22++%7D&charset=utf-8&format=json&method=alipay.trade.pay&sign=HNvxT61qlpl9MWY2ozYVLGbm5P6a%2BdY6ed8pv19zuo0ilqTLvh5HYJy7kvwzJamjOttJA1%2BfY2l3%2BR1xtMv6rLx9oa8evZ0wmdDYCyuK3hoA2FVVAE5Ad3izfGkb7COIMBYcFRoCJQrbkB%2BZhzljk5seD7M5JIDONpeQ2mx0cKBoXk4iJ8TmfldxeYCXQsuoVMLeWqjylJa%2BRSbeEQgyTaxvMHcFXVY%2Fe1VkADeckD2J4a5Dy3otS6h5MqpEliidglsasdJQqHVYvDVGkePabmp%2FJY4E2RZKA%2BCGxcEjyhFWsoy3%2FW1ijuXLk%2BBAfmcnIl4p%2BDAmX5AiNPVjDE4Isg%3D%3D&sign_type=RSA2&timestamp=2018-05-08+13%3A39%3A38&version=1.0&sign=HNvxT61qlpl9MWY2ozYVLGbm5P6a%2BdY6ed8pv19zuo0ilqTLvh5HYJy7kvwzJamjOttJA1%2BfY2l3%2BR1xtMv6rLx9oa8evZ0wmdDYCyuK3hoA2FVVAE5Ad3izfGkb7COIMBYcFRoCJQrbkB%2BZhzljk5seD7M5JIDONpeQ2mx0cKBoXk4iJ8TmfldxeYCXQsuoVMLeWqjylJa%2BRSbeEQgyTaxvMHcFXVY%2Fe1VkADeckD2J4a5Dy3otS6h5MqpEliidglsasdJQqHVYvDVGkePabmp%2FJY4E2RZKA%2BCGxcEjyhFWsoy3%2FW1ijuXLk%2BBAfmcnIl4p%2BDAmX5AiNPVjDE4Isg%3D%3D
			13:39:40.512 [main] INFO com.mixiusi.util.AlipayUtils - {"alipay_trade_pay_response":{"code":"10000","msg":"Success","buyer_logon_id":"130****9787","buyer_pay_amount":"0.01","buyer_user_id":"2088802784422153","fund_bill_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"gmt_payment":"2018-05-08 13:39:35","invoice_amount":"0.01","out_trade_no":"20180508133938","point_amount":"0.00","receipt_amount":"0.01","total_amount":"0.01","trade_no":"2018050821001004150540519241"},"sign":"N/swLSNmUijVrx9reWli8UHM1fHsYb4T8gVThtetP1K84n36nyaosFvtQshOH/swiH3QHZL/Cqi0IRdibFOsnOUnEN+JLEq9FIBR6L29eQ84hTqNdNVzZgdaLLJ9VEo3NztHn8dgiqx9lm8+P82vv3C2qXD6a8I/UAI8TSO3v3AwdT5XOTGIkPUVr2r3UW2PQDYWligA3zbU86sSD1ohOPGIICdwdCLNoNOJ0JDWoZK/G5Ecqcwn9Wl2Fh4UGjOEHaAcmWlaE71IK656GqpYBL65WjdUZAY8lXhMTe4W6YqbgX6HWHSdMdjtrv4KN59Xk+0p8nAt3h5AS5kS/y4wuQ=="}---支付宝返回的交易号：2018050821001004150540519241
			{"alipay_trade_pay_response":{"code":"10000","msg":"Success","buyer_logon_id":"130****9787","buyer_pay_amount":"0.01","buyer_user_id":"2088802784422153","fund_bill_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"gmt_payment":"2018-05-08 13:39:35","invoice_amount":"0.01","out_trade_no":"20180508133938","point_amount":"0.00","receipt_amount":"0.01","total_amount":"0.01","trade_no":"2018050821001004150540519241"},"sign":"N/swLSNmUijVrx9reWli8UHM1fHsYb4T8gVThtetP1K84n36nyaosFvtQshOH/swiH3QHZL/Cqi0IRdibFOsnOUnEN+JLEq9FIBR6L29eQ84hTqNdNVzZgdaLLJ9VEo3NztHn8dgiqx9lm8+P82vv3C2qXD6a8I/UAI8TSO3v3AwdT5XOTGIkPUVr2r3UW2PQDYWligA3zbU86sSD1ohOPGIICdwdCLNoNOJ0JDWoZK/G5Ecqcwn9Wl2Fh4UGjOEHaAcmWlaE71IK656GqpYBL65WjdUZAY8lXhMTe4W6YqbgX6HWHSdMdjtrv4KN59Xk+0p8nAt3h5AS5kS/y4wuQ=="}
			10000---null---2018050821001004150540519241
			
		* 查询
		* 
		* 20180508134948
			13:49:49.002 [main] DEBUG sdk.biz.err - 2018-05-08 13:49:48^_^https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018022802289573&biz_content=%7B++++%22out_trade_no%22%3A%2220180508133938%22%2C++++%22trade_no%22%3A%222018050821001004150540519241%22%7D&charset=utf-8&format=json&method=alipay.trade.query&sign=YWshQeeB9tnuIWPiDfcfXznlU8rdNkc3FBaykMaE5vXgKSJGuNH4ptOWej%2BbJF7SxSj3untRBhJKglqAMNhZSh6ELYeCqvlCdXRP213fmhixjs3yefwU8fd%2BpLRPXl5JjDP4QSPrOoyemeFml7LCfqj9tZRzof%2FQ23jGHNUtf%2BzGlpXNIMkcSnGAq%2Fs%2FPHwuYG0vUN%2BuWO0Gwb4V2IXc4iZ8DZ7DmHdDvv2ayMdcL9yjgrTb2iZuHHjFl95683xFSWP84IN%2BuqAvpdDg44ACzc%2BqLULABJ%2BjIo%2FO9zEmPSup5VFvC5nXmQ%2FygOGZIIgJkJmiXgUa4LuB4C4GtgOq3w%3D%3D&sign_type=RSA2&timestamp=2018-05-08+13%3A49%3A48&version=1.0&sign=YWshQeeB9tnuIWPiDfcfXznlU8rdNkc3FBaykMaE5vXgKSJGuNH4ptOWej%2BbJF7SxSj3untRBhJKglqAMNhZSh6ELYeCqvlCdXRP213fmhixjs3yefwU8fd%2BpLRPXl5JjDP4QSPrOoyemeFml7LCfqj9tZRzof%2FQ23jGHNUtf%2BzGlpXNIMkcSnGAq%2Fs%2FPHwuYG0vUN%2BuWO0Gwb4V2IXc4iZ8DZ7DmHdDvv2ayMdcL9yjgrTb2iZuHHjFl95683xFSWP84IN%2BuqAvpdDg44ACzc%2BqLULABJ%2BjIo%2FO9zEmPSup5VFvC5nXmQ%2FygOGZIIgJkJmiXgUa4LuB4C4GtgOq3w%3D%3D
			13:49:49.589 [main] INFO com.mixiusi.util.AlipayUtils - {"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"130****9787","buyer_pay_amount":"0.01","buyer_user_id":"2088802784422153","fund_bill_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"invoice_amount":"0.01","out_trade_no":"20180508133938","point_amount":"0.00","receipt_amount":"0.01","send_pay_date":"2018-05-08 13:39:35","total_amount":"0.01","trade_no":"2018050821001004150540519241","trade_status":"TRADE_SUCCESS"},"sign":"K+UqOErl4DDMYtaoHCOB4gu0tYNSutSKDgh4ph8ytUNkyQ0K0ezRuHzTMKBu0lxsd+JU2PQ/HjjpxgGKNDnkhNFDRRH68atxmx1R6KTE9wgfl1kaGL9T8ghJBs1clUp+x4wxkxH/uVwSCLj5hg68v273t7l+qNTct+fOGC9TosJb3LvTEAQO92Y5vN1bXiBItDxQCNPOG7X0gJC62VlA5Kv+xbprbhQDMQ8Ff1E17KEsiUXQkn+FfWEZBznsfWpV2av0wvsG+m5Fu9zzZyqmC27PsLn/ngb9x1oFQjkG6+emFezQSUYZTQiufLtHIoLwA9PQXqBhcXXQ8EAxQYnNpA=="}---TradeStatus : TRADE_SUCCESS
			{"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"130****9787","buyer_pay_amount":"0.01","buyer_user_id":"2088802784422153","fund_bill_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"invoice_amount":"0.01","out_trade_no":"20180508133938","point_amount":"0.00","receipt_amount":"0.01","send_pay_date":"2018-05-08 13:39:35","total_amount":"0.01","trade_no":"2018050821001004150540519241","trade_status":"TRADE_SUCCESS"},"sign":"K+UqOErl4DDMYtaoHCOB4gu0tYNSutSKDgh4ph8ytUNkyQ0K0ezRuHzTMKBu0lxsd+JU2PQ/HjjpxgGKNDnkhNFDRRH68atxmx1R6KTE9wgfl1kaGL9T8ghJBs1clUp+x4wxkxH/uVwSCLj5hg68v273t7l+qNTct+fOGC9TosJb3LvTEAQO92Y5vN1bXiBItDxQCNPOG7X0gJC62VlA5Kv+xbprbhQDMQ8Ff1E17KEsiUXQkn+FfWEZBznsfWpV2av0wvsG+m5Fu9zzZyqmC27PsLn/ngb9x1oFQjkG6+emFezQSUYZTQiufLtHIoLwA9PQXqBhcXXQ8EAxQYnNpA=="}
			10000---null---2018050821001004150540519241
		 *//*
		String time = DateUtils.dateFormat("yyyyMMddHHmmss");
		System.out.println(time);
		Double price = 0.01;
		try {
//			AlipayTradePayResponse resp = alipayByBarCode(time, "285359210267119248", price);
			AlipayTradeQueryResponse resp = alipayTradeQuery("20180508133938", "2018050821001004150540519241");
			System.out.println(resp.getBody());
			System.out.println(resp.getCode() + "---" + resp.getSubCode() + "---" + resp.getTradeNo());
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			log.info(e.getErrMsg());
		}
	}
	*/
}
