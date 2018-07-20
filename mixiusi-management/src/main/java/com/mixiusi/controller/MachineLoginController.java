package com.mixiusi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.LoginInfo;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MD5;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.LoginInfoService;

@RestController
@RequestMapping("/coffeelogin")
public class MachineLoginController {
	private final Logger log = LoggerFactory.getLogger(MachineLoginController.class);
	@Reference
	private LoginInfoService loginInfoService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 添加咖啡机的登陆用户
	 * @param venderName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/addLogin", method = RequestMethod.POST)
	public ResultBean login(String venderName, String password) {
		  String pwd = MD5.md5(password);
		  LoginInfo li = new LoginInfo();
		  li.setVenderName(venderName);
		  li.setVenderPassword(pwd);
		  loginInfoService.addLoginInfo(li);
	      return ResultBean.ok();
	}
}
