package com.mixiusi.handler;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.utils.Enums.Login;
import com.mixiusi.protocol.ResponseCode;
import com.mixiusi.protocol.request.LoginRequest;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.response.Loginresponse;
import com.mixiusi.service.LoginInfoService;

//登录处理
@Component
public class LoginRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(LoginRequestHandler.class);
	@Reference
	private LoginInfoService loginService;
	private static LoginInfoService loginInfoService;
	
	@PostConstruct
	public void init() {
		loginInfoService = loginService;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("---------------登陆请求-----------------");
		short status = 0;
		// core.cancelRequestRetryTimer(request.getLinkFrame().serialId);
		LoginRequest loginRequest = (LoginRequest) request;
		Loginresponse loginresponse = new Loginresponse(loginRequest.getUid());
		loginresponse.getLinkFrame().serialId = loginRequest.getLinkFrame().serialId;
		String userName = loginRequest.getUid();
		String password = loginRequest.getPassword();
		Login result = loginInfoService.queryUserInfo(userName, password);
		log.info(loginRequest.getUid() + "----"+loginRequest.getPassword());
		log.info("------登陆状态-----" + result);
		if (result == Login.USER_NOT_EXIST) {
			loginresponse.getLinkFrame().resCode = ResponseCode.RES_ENONEXIST;
		} else if (result == Login.PASSWORD_ERROR) {
			loginresponse.getLinkFrame().resCode = ResponseCode.RES_EUIDPASS;
		} else if (result == Login.LOGIN_SUCCESS){
			loginresponse.getLinkFrame().resCode = ResponseCode.RES_SUCCESS;
		}else {
			loginresponse.getLinkFrame().resCode = ResponseCode.RES_ENONEXIST;
		}

		loginresponse.setsessionId(loginRequest.getUid());
		loginresponse.setstatus(status);
		loginresponse.setvendorname(loginRequest.getUid());

		ctx.getChannel().write(loginresponse);

	}

}
