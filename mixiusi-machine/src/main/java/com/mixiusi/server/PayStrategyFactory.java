package com.mixiusi.server;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import com.mixiusi.inter.Pay;
import com.mixiusi.service.PayStrategyService;

/**
 * 支付方式实现类获取工厂
 * @author liukun
 *
 */
public class PayStrategyFactory {
	
	private static PayStrategyFactory factory = new PayStrategyFactory();

	private PayStrategyFactory() {
	}
	
	public static PayStrategyFactory getInstance() {
		return factory;
	}
	
	public static HashMap<Integer, String> source_map = new HashMap<>();

	static {
		Reflections ref = new Reflections ("com.mixiusi.payImpl");//扫描接口实现类的包
		//获取带注解的类
		Set<Class<?>> classList =  ref.getTypesAnnotatedWith(Pay.class);
		//根据注解的值将类放到map中
		for(Class clazz :  classList){
			Pay pay = (Pay)clazz.getAnnotation(Pay.class);
			source_map.put(pay.value(), clazz.getCanonicalName());//拿到类的全名
		}
	}
	
	public PayStrategyService creator(Integer payMethod) throws Exception{
		String clazz = source_map.get(payMethod);
		Class clazz_ = Class.forName(clazz);
		return (PayStrategyService) clazz_.newInstance();
	}
}
