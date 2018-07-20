package com.mixiusi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Permission;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.User;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Reference
	private UserService userService;
	@Autowired
	private ApplicationProperties application;

	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResultBean login(@RequestParam String username, @RequestParam String password,
                     HttpServletRequest request) {
//		  String pwd = MD5.md5(password);
		  User user = userService.findByUserNamePassword(username, password);
		  if (user == null) {
			  return ResultBean.error(MxsConstants.CODE1, "登录名或密码错误！");
		  }
		  List<Permission> permissions = userService.listUserPermissions(user.getId());
		  HttpSession session = request.getSession();
		  String sessionId = session.getId();
	      request.getSession().setAttribute("LOGIN_USER", user);
	      request.getSession().setAttribute("LOGIN_PERMISSIONS", permissions);
	      log.info("--User--" + request.getSession().getAttribute("LOGIN_USER").toString());
	      return ResultBean.ok(sessionId);
  }
	  /**
	   * 退出登陆
	   * @param request
	   * @return
	   */
	  @RequestMapping("/logout")
	  public ResultBean logout(HttpServletRequest request) {
	    request.getSession().removeAttribute("LOGIN_USER");
	    request.getSession().removeAttribute("LOGIN_PERMISSIONS");
	    return ResultBean.ok();
	  }
}
