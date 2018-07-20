package com.mixiusi.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.User;
import com.mixiusi.bean.utils.MxsConstants;

/**
 * 登陆后的请求验证
 * @author liukun
 *
 */
//@Configuration
//@Aspect
public class PermissionValidationAop {
	private static Logger log = LoggerFactory.getLogger(PermissionValidationAop.class);

	 @Around("execution (* com.mxs.mainpage.controller..*.*(..))")
	 public Object filterAop(ProceedingJoinPoint pro) throws Throwable {
		  log.info("-------开始执行跨域问题----------");
	      //获取response
	      HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	      //核心设置
	      response.setHeader("Access-Control-Allow-Origin", "*");
		  response.setHeader("Access-Control-Allow-Headers", "*");
	      //执行调用的方法
	      Object proceed = pro.proceed();
	      return proceed;
	 }
	
	@Around(value = "execution (* com.mixiusi.servicemanagement.controller..*(..))")
	public Object validaitonRequest(ProceedingJoinPoint pro) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = request.getRequestURL().toString();
	    if (!url.contains("/login")) {//登陆和退出的请求不进行权限校验
	    @SuppressWarnings("unchecked")
		List<Permission> permissions = (List<Permission>) request.getSession().getAttribute("LOGIN_PERMISSIONS");
	    User user = (User) request.getSession().getAttribute("LOGIN_USER");
	    if (permissions == null || user == null) {
	    	return ResultBean.error(MxsConstants.CODE0, "登陆失效，请重新登陆");
	    }
	    log.info("-AOP----" + user.toString());
	    
	    // 如果是admin，就不需要权限验证
	    boolean hasPermission = false;
	    if (user.getIsAdmin()) {//判断是否是admin用户
	    	hasPermission = true;
	    }else {//不是admin用户则进行权限校验
	    	for (Permission permission : permissions) {
	    		if (url.contains(permission.getUrl())) {
	    			hasPermission = true;
	    			break;
	    		}
	    	}
	    }
	    if (!hasPermission) {
	    	return ResultBean.error(MxsConstants.CODE0, user.getUserName() + "没有权限访问" + url);
	    }
		      
	  }
	    //执行拦截方法，并返回执行的结果
	    Object proceed = pro.proceed();	
	    return proceed;
	}
}
