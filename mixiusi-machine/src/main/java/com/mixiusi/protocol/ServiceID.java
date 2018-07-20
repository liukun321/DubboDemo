package com.mixiusi.protocol;

//核心服务编号分配，集中在丿起，便于规划
//协议使用 uint16_t 存贮服务编号〿
//[1-999] 客户端和服务器鿚讯的服务号
//[1000-4999] 本地服务号
//[5000-9999] 服务器扩展服务号
//[10000-10499] P2P相关服务号
//[20000-24999] 插件服务号

public class ServiceID {
	public static final short

	SVID_NODEF = 0, // 保留

	SVID_PRES = 10, // 老版状态服务号
	SVID_NOTIFY = 12, 
	SVID_SESSION = 50, // 会话服务
	
	SVID_LITE_LINK = 0x50, // 连接服务
	SVID_LITE_AUTH = 0x51, // 验证和登彿
	
	SVID_LITE_COFFEE = 0x60, //咖啡信息
	
	SVID_MAX = 0xFF; // ServiceID只有8位，这是能表达的朿大忿255，不使用
}
