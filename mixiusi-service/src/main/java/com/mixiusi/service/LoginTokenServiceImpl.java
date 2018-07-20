package com.mixiusi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.LoginToken;
import com.mixiusi.biz.LoginTokenBiz;
@Service(interfaceClass = LoginTokenService.class)
public class LoginTokenServiceImpl implements LoginTokenService {
	@Autowired
	private LoginTokenBiz loginTokenBiz;

	@Override
	public LoginToken addLoginToken(LoginToken loginToken) {
		return loginTokenBiz.addLoginToken(loginToken);
	}

	@Override
	public LoginToken query(String workerId) {
		return loginTokenBiz.query(workerId);
	}

	@Override
	public void setAllByStatus() {
		loginTokenBiz.setAllByStatus();
	}

}
