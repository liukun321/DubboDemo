package com.mixiusi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.LoginInfo;
import com.mixiusi.bean.utils.Enums.Login;
import com.mixiusi.biz.LoginInfoBiz;

@Service(interfaceClass = LoginInfoService.class)
public class LoginInfoServiceImpl implements LoginInfoService {
	private final Logger log = LoggerFactory.getLogger(LoginInfoServiceImpl.class);
	@Autowired
	private LoginInfoBiz loginInfoBiz;

	@Override
	public Login queryUserInfo(String venderName, String venderPassword) {
		return loginInfoBiz.queryUserInfo(venderName, venderPassword);
	}

	@Override
	public LoginInfo addLoginInfo(LoginInfo loginInfo) {
		return loginInfoBiz.addLoginInfo(loginInfo);
	}

}
