package com.mixiusi.service;

import com.mixiusi.bean.LoginToken;

public interface LoginTokenService {
	LoginToken addLoginToken(LoginToken loginToken);
	
	LoginToken query(String workerId);

	void setAllByStatus();
}
