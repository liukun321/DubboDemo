package com.mixiusi.protocol.enums;

import com.mixiusi.protocol.ServiceID;

public interface ISessionService {
	public interface CommandId {
		public static final short CID_SAY = 0x07; // 单人会话消息

		public static final short CID_ACK = 0x0A; // 删除离线消息协议
		
		public static final short CID_MASS_SAY = 13; // 群发点对点消恿

		public static final short CID_ACTIVE = 20; // 正在输入
		
		public static final short CID_SAY_ACK = 50; // 单人会话消息的结枿
		
		public static final short CID_SAY_MASS_ACK = 51; // 群发消息的结枿
		
		public static final short CID_SAY_READ = 54; // 消息已读标记给服务器
		
		public static final short CID_LITE_SAY_READ_FEEDBACK = 55; // 服务器反馈的消息已读
	};

	public interface TagId {
		
		/*
		public static final int TAG_MSGID = ServiceID.SVID_SESSION << 16 | 1;

		public static final int TAG_MSGTYPE = ServiceID.SVID_SESSION << 16 | 2;

		public static final int TAG_CMDTYPE = ServiceID.SVID_SESSION << 16 | 3;

		public static final int TAG_MSGBODY = ServiceID.SVID_SESSION << 16 | 4;

		public static final int TAG_COUNT = ServiceID.SVID_SESSION << 16 | 5;

		public static final int TAG_LASTPEER = ServiceID.SVID_SESSION << 16 | 6;

		public static final int TAG_FLAG = ServiceID.SVID_SESSION << 16 | 7;

		public static final int TAG_TIME = ServiceID.SVID_SESSION << 16 | 8;

		public static final int TAG_FONT = ServiceID.SVID_SESSION << 16 | 9;

		public static final int TAG_DEVICETYPE = ServiceID.SVID_SESSION << 16 | 16;// 设备类型

		public static final int TAG_SESSION_KEEP = ServiceID.SVID_SESSION << 16 | 20; // 是否保留离线消息

		public static final int TAG_SESSION_AUTH = ServiceID.SVID_SESSION << 16 | 21; // 是否进行权限验证
	*/	
		
		public static final int MSGID_CLIENT	 = ServiceID.SVID_SESSION << 16 | 301;  // 客户端msgid, 用于客户端去釿
        public static final int SENDER           = ServiceID.SVID_SESSION << 16 | 302;
        public static final int SENDER_TYPE      = ServiceID.SVID_SESSION << 16 | 303;
        public static final int RECIEVER         = ServiceID.SVID_SESSION << 16 | 304;
        public static final int RECIEVER_TYPE    = ServiceID.SVID_SESSION << 16 | 305;
        public static final int TIME             = ServiceID.SVID_SESSION << 16 | 306;
        public static final int MESSAGE_TAG      = ServiceID.SVID_SESSION << 16 | 307;
        public static final int MESSAGE_TYPE     = ServiceID.SVID_SESSION << 16 | 308;
        public static final int ENCODE           = ServiceID.SVID_SESSION << 16 | 309;
        public static final int FONT             = ServiceID.SVID_SESSION << 16 | 310;
        public static final int FONT_SIZE        = ServiceID.SVID_SESSION << 16 | 311;
        public static final int MESSAGE_BODY     = ServiceID.SVID_SESSION << 16 | 312;
        public static final int EFFECTS          = ServiceID.SVID_SESSION << 16 | 313;
        public static final int COLOR            = ServiceID.SVID_SESSION << 16 | 314;
        public static final int NOTIFY           = ServiceID.SVID_SESSION << 16 | 315;
        public static final int LBS_COORDINATE   = ServiceID.SVID_SESSION << 16 | 500; // lbs经度纬度坐标＿ LBS信息与翼聊不兼容
        public static final int LBS_CONTENT		 = ServiceID.SVID_SESSION << 16 | 502; // lbs多媒体描述信恿
        public static final int MSGID_SERVER	 = ServiceID.SVID_SESSION << 16 | 503; // 服务器的msg id，用于离线消息置使
        public static final int RESEND			 = ServiceID.SVID_SESSION << 16 | 504; // 重发标记
	}

	/*
	public interface MessageType {
		public static final int MSGTYPE_TEXT = 1; // 文本

		public static final int MSGTYPE_FILETRANS = 2; // 文件传输

		public static final int MSGTYPE_AUDIO = 3; // 语音

		public static final int MSGTYPE_VIDEO = 4; // 视频

		public static final int MSGTYPE_COMIC = 5; // 动漫

		public static final int MSGTYPE_ACTION = 6; // 快捷回复的小动作

		public static final int MSGTYPE_SYSINFO = 7; // 系统信息 包括网络状濁等
		public static final int SessionMsgType_PPNX = 8; // 新ppnx消息
		public static final int SessionMsgType_OfflineFile = 9;// 离线文件消息

		public static final int MSGTYPE_PICTURE = 10; // 图片
		
		public static final int MSGTYPE_LOCATION = 11; // 位置
	};*/
	
	public interface MessageType {
		public static final String TEXT = "0";
		public static final String MEDIA = "Media";
		public static final String MULTEXT = "MulText";
	};
	
	public interface MessageTypeLite {
		public static final int PICTURE = 1;
		public static final int AUDIO = 2;
		public static final int VIDEO = 3;
		public static final int LOCATION = 4;
	}
}
