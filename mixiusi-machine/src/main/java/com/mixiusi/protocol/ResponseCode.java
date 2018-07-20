package com.mixiusi.protocol;

/**
 * 鍝嶅簲浠ｇ爜瀹氫箟
 * 
 */
public final class ResponseCode {
	/*
	 * DB/Task错误代码
	 */
	public static final short RES_SUCCESS = 200;

	public static final short RES_EVERSION = 201; // 客户端版本不寿

	public static final short RES_ENOTINVITE = 300; // 用户没有被邀诿

	public static final short RES_EBAN = 301; // 用户在黑名单丿

	public static final short RES_EUIDPASS = 302; // 密码错误

	public static final short RES_EUREG_EXIST = 303; // 要注册的账号已存圿

	public static final short RES_EUREG_NEXIST = 304; // 要注册的账号不已存在

	public static final short RES_ADDR_BLOCKADED = 310; // 登录IP或MAC被封锿

	public static final short RES_IP_NOT_ALLOWED = 315; // 内部帐户不允许在该地坿登陆

	public static final short RES_UID_OR_PASS_ERROR = 316; // 用户名不存在或密码错诿
	
	public static final short RES_FORBIDDEN = 403; // 用户被封禿 

	public static final short RES_ENONEXIST = 404; // 目标(对象或用房)不存圿

	public static final short RES_EACCESS = 405; // 没有权限操作

	public static final short RES_ETIMEOUT = 408; // 超时

	public static final short RES_EPARAM = 414; // 参数错误

	public static final short RES_ECONNECTION = 415; // 网络连接出现问题

	public static final short RES_EFREQUENTLY = 416; // 操作太过频繁

	public static final short RES_EEXIST = 417; // 对象已经存在

	public static final short RES_EHTTP = 418; // http协议访问错误

	public static final short RES_ESIZE_LIMIT = 419; // 大小超过限制

	public static final short RES_EOP_EXCEPTION = 420; // 操作出现异常

	public static final short RES_EUNKNOWN = 500; // 不知道什么错诿

	public static final short RES_ENOTENOUGH = 507; // 不足

	public static final short RES_OVERDUE = 508; // 超过期限

	public static final short RES_INVALID = 509; // 已经失效

	public static final short RES_USER_NOT_EXIST = 510; // 用户不存在
}
