package com.mixiusi.protocol.enums;

import com.mixiusi.protocol.ResponseCode;
import com.mixiusi.protocol.ServiceID;

public interface IAuthService {
	
	public interface CommandId {
		public static final short CID_REQ_CODE = 1; // 请求验证砿
		public static final short CID_VERYFI_CODE = 2; // 验证验证砿
		public static final short CID_LOGIN = 3; // 登录
		public static final short CID_KICKOUT = 4; // 踢人
	}

	public interface KickoutReason {
		public static final short Normal = 1; // 正常互踢
		public static final short Block = 2; // 被禁甿
		public static final short Shutup = 3; // 被禁訿
		public static final short PwdExpired = ResponseCode.RES_EUIDPASS; // 密码错，过期了？
		public static final short UserNotExist = ResponseCode.RES_ENONEXIST; // 用户不存圿
		public static final short LoginError = -1; // 登录失败，未知原囿
	}

	public interface ClientType {
		public static final int PC_Windows = 1;
		public static final int PC_Linux = 2;
		public static final int PC_MAC = 3;
		public static final int Wap = 4;
		public static final int iPhone = 5;
		public static final int iOS_HD_iPad = 6;
		public static final int Android = 7;     // Android版本
		public static final int Windows_Mobile = 8;
		public static final int Symbian = 9;
		public static final int Brew = 10;
		public static final int Windows_8 = 11;

		public static final int Android_Lite = 50;
		public static final int IPhone_Lite = 51;
	}

	public interface RequestType {
		public static final int InitPassword = 1; // 第一次验证完毕后登录，初始化密码
		public static final int VerifyPassword = 2; // 正常情况，验证密砿
	}
	
	public interface LoginRetType {
		public static final short NOTUSER = 0;  //非用房
		public static final short IAMMIXIN = 1; //普鿚用房
		public static final short IAMADMIN = 2; //管理员用房
	}

	// 在线状濿
	public static final int presid = ServiceID.SVID_PRES << 16 | 1;

	// 客户端设备类垿
	public static final int DEVICE_TYPE = ServiceID.SVID_PRES << 16 | 19;

	// 企业类型
	public static final int SERVICE_TYPE = ServiceID.SVID_PRES << 16 | 20;

	// 协议版本叿
	public static final int protocolVersion = ServiceID.SVID_PRES << 16 | 3;

	public static final int dataVersion = ServiceID.SVID_PRES << 16 | 4;

	// 是否有摄像头
	public static final int hasVideo = ServiceID.SVID_PRES << 16 | 5;

	// 网络连接状况
	public static final int netState = ServiceID.SVID_PRES << 16 | 6;

	// 记录本次登录的时闿
	public static final int loginTime = ServiceID.SVID_PRES << 16 | 7;

	// 外部IP
	public static final int outterIP = ServiceID.SVID_PRES << 16 | 11;

	// 内部IP
	public static final int innerIP = ServiceID.SVID_PRES << 16 | 12;

	// 客户端操作系绿
	public static final int os = ServiceID.SVID_PRES << 16 | 13;

	// 客户端网卡地坿
	public static final int MAC = ServiceID.SVID_PRES << 16 | 14;

	// 用户登录级别（普通，轻量＿
	public static final int loginLevel = ServiceID.SVID_PRES << 16 | 15;

	// 客户端版本号
	public static final int clientVersion = ServiceID.SVID_PRES << 16 | 16;

	// PPN文件传输版本叿
	public static final int ppnFileVersion = ServiceID.SVID_PRES << 16 | 17;
	// PPN图片传输版本叿
	public static final int ppnPictureVersion = ServiceID.SVID_PRES << 16 | 18;
	// 连接机IP
	public static final int linkIP = ServiceID.SVID_PRES << 16 | 102;

	// 连接机Innert Port
	public static final int linkPort = ServiceID.SVID_PRES << 16 | 103;

	// 连接叿
	public static final int consid = ServiceID.SVID_PRES << 16 | 104;

	// ip对应的地坿
	public static final int netAddress = ServiceID.SVID_PRES << 16 | 105;

	// 备注，网络类型或机构单位
	public static final int netMemo = ServiceID.SVID_PRES << 16 | 106;

	// 记录执行过的步骤 ,warning 翼聊版是110 ,lite版改房210
	public static final int checkPoint = ServiceID.SVID_PRES << 16 | 210;

	// 客户端最后一次发送心跳过来的时间
	public static final int last_heartbeat = ServiceID.SVID_PRES << 16 | 220;

	public static final int clientType = ServiceID.SVID_PRES << 16 | 301;
	public static final int version = ServiceID.SVID_PRES << 16 | 302;
	public static final int versionType = ServiceID.SVID_PRES << 16 | 303;
	public static final int language = ServiceID.SVID_PRES << 16 | 304;
	public static final int encode = ServiceID.SVID_PRES << 16 | 305;
	public static final int tag = ServiceID.SVID_PRES << 16 | 306;
	public static final int channel = ServiceID.SVID_PRES << 16 | 307;
	public static final int sdk = ServiceID.SVID_PRES << 16 | 308;
	public static final int token = ServiceID.SVID_PRES << 16 | 309;
	public static final int account = ServiceID.SVID_PRES << 16 | 300;
	public static final int accountType = ServiceID.SVID_PRES << 16 | 311;
	public static final int status = ServiceID.SVID_PRES << 16 | 312;
	public static final int subscribe = ServiceID.SVID_PRES << 16 | 313;
	public static final int realId = ServiceID.SVID_PRES << 16 | 314;
	public static final int sessionId = ServiceID.SVID_PRES << 16 | 315;
	public static final int disconnected = ServiceID.SVID_PRES << 16 | 316;
}
