package com.mixiusi.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.LoginToken;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.service.LoginTokenService;

/**
 * 登陆后的请求验证
 * @author liukun
 *
 */
@Configuration
@Aspect
public class LoginValidationAop {
	private static Logger log = LoggerFactory.getLogger(LoginValidationAop.class);
//	private static Map<String, HttpSession> sessionMap =  new HashMap();
	@Reference
	private LoginTokenService loginTokenService;
	/**
	 * 环绕通知，当请求被拦截，可以代替目标方法返回
	 * 通过则通过返回ProceedingJoinPoint的process返回目标方法执行后的返回值
	 * @param pro
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution (* com.mixiusi.controller.EmployeeController.user*(..))")
	public Object validaitonRequest(ProceedingJoinPoint pro) throws Throwable {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
//			HttpSession session = request.getSession();
			String token = "", phone = "";
//			token = (String) session.getAttribute("tokens");
//			phone = (String) session.getAttribute("phone");
//			log.info(session.getId() + " --- " + token + " --- " + phone);
			String new_tokens = request.getParameter("token");
			String workerId = request.getParameter("workerId");
			LoginToken lt = loginTokenService.query(workerId);
			if(null == lt)
				return ResultBean.error(MxsConstants.CODE, phone + "登陆失效，请重新登陆");
			token = lt.getToken();
			log.info("token = " + token + " new_tokens = " + new_tokens);
			if (token.equals(new_tokens) && !StringUtils.isNull(token)) {
				Object proceed = pro.proceed();
				return proceed;
			} else {
				return ResultBean.error(MxsConstants.CODE, phone + "登陆失效，请重新登陆");
			} 
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResultBean.error(MxsConstants.CODE, "登陆失效，请重新登陆");
		}
	}
}
