package com.mixiusi.biz;

import com.mixiusi.repository.read.LoginInfoReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.LoginInfo;
import com.mixiusi.bean.utils.Enums.Login;
import com.mixiusi.repository.write.LoginInfoRepository;
@Service
public class LoginInfoBiz{
	private final Logger log = LoggerFactory.getLogger(LoginInfoBiz.class);
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Autowired
	private LoginInfoReadRepository loginInfoReadRepository;

	public Login queryUserInfo(String venderName, String venderPassword) {
		try {
			LoginInfo loginInfo = loginInfoReadRepository.findByVenderName(venderName);
			if (null == loginInfo) {
				return Login.USER_NOT_EXIST;
			} else if (venderPassword.equals(loginInfo.getVenderPassword())) {//MD5.md5(result)
				return Login.LOGIN_SUCCESS;
			} else {
				return Login.PASSWORD_ERROR;
			}
		}catch(Exception e) {
			log.info(e.getMessage());
			return Login.LOGIN_EXCEPTION;
		}
	}

	public LoginInfo addLoginInfo(LoginInfo loginInfo) {
		return loginInfoRepository.save(loginInfo);
	}
}
