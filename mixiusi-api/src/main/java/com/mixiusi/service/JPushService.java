package com.mixiusi.service;
/**
 * 此接口为极光推送的实现，用于对商户app的推送
 * @author liukun
 *
 */
public interface JPushService {
	
	/**
	 * 给指定唯一商户app推送通知
	 * @param registrationId  唯一用户id，需调用Android的接口获取当前商户app的唯一id
	 * @param notification_alert  推送内容(必填)	
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param extrasparam  此字段为透传字段，不会显示在通知栏。
	 *     用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return 0推送失败   1推送成功
	 */
	int sendToRegistrationId(String registrationId, String notification_alert, String notification_title, String extrasparam);
	
	/**
	 * 推送给所有商户app
	 * @param notification_alert  推送内容(必填)
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param extrasparam  此字段为透传字段，不会显示在通知栏。
	 *     用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return  0推送失败   1推送成功
	 */
	int sendToAll(String notification_alert, String notification_title, String extrasparam);
	
	/**
	 * 推送给所有ios用户
	 * @param notification_alert  推送内容(必填)
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param extrasparam  此字段为透传字段，不会显示在通知栏。
	 *     用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return  0推送失败   1推送成功
	 */
	int sendToAllIos(String notification_alert, String notification_title, String extrasparam);
	
	/**
	 * 推送给所有安卓用户
	 * @param notification_alert  推送内容(必填)
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param extrasparam  此字段为透传字段，不会显示在通知栏。
	 *     用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return  0推送失败   1推送成功
	 */
	int sendToAllAndroid(String notification_alert, String notification_title, String extrasparam);
}
