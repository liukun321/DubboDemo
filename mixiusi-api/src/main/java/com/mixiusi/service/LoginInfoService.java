package com.mixiusi.service;

import com.mixiusi.bean.LoginInfo;
import com.mixiusi.bean.utils.Enums.Login;

public interface LoginInfoService {
	/**
	 * 查询机器端登陆信息
	 * @param venderName
	 * @param venderPassword
	 * @return
	 */
	Login queryUserInfo(String venderName, String venderPassword);
	/**
	 * 添加机器端登陆信息
	 * @param loginInfo
	 * @return
	 */
	LoginInfo addLoginInfo(LoginInfo loginInfo);
}
