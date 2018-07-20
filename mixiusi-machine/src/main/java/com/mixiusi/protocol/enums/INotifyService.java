package com.mixiusi.protocol.enums;

public interface INotifyService {

	public interface CommandId {
		// CTOC 表示发鿁方目前在线, STOC 表示发鿁方目前不在线.
		public static final short CTOC = 1; // client to client
		public static final short STOC = 2; // server to client
		public static final short DELETE = 3; // delete offline message
												// indatabase
		public static final short GET_ALL = 4; // get all offline messages in
												// database
		public static final short GET_ALL_WEB = 5; // get all offline messages
													// in database, to web mail
													// popo
		
		public static final short RESET_OFFLINEMSG = 6; // 重置离线消息已接受状态未未接叿
		
		public static final short GET_ALL_MOBILE  =7; //离线消息获取 聚合接口
		public static final short GET_ALL_NOTIFY  = 8; // 获取承有离线鿚知的返回包
		public static final short GET_ALL_MESSAGE = 9; // 获取承有离线消息的返回匿
	};
}