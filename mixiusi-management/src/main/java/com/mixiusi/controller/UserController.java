package com.mixiusi.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.User;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.vo.UserVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.PermissionService;
import com.mixiusi.service.UserService;
import com.mixiusi.vo.UseVo;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 * @author liukun
 *
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@Reference
	private UserService userService;
	@Reference
	private PermissionService permissionService;
	
	@Autowired
	private ApplicationProperties application;
	/**
	 * 获取所有用户信息列表
	 * @return
	 */
	@GetMapping("/getAll")
	public ResultBean list(UserVo uvo) {
		Integer page = uvo.getPage();
		Integer size = uvo.getSize();
		List<User> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
    	list = userService.findAll(uvo);
    	Long sum = userService.findUserNumber(uvo);
    	if(null == list)
    		return ResultBean.error();
    	return ResultBean.ok(size, page, sum, list);
	}
	/**
	 * 添加用户的同时为其分配权限
	 * 权限从权限表中选择的，
	 * 用户权限关联的表也同时更新
	 * @param userDto
	 * @return
	 */
	@GetMapping("/add")
	public ResultBean addUser(@Validated UseVo userDto) {
		try {
			User temp = userService.findByUserName(userDto.getUsername());
			if (temp != null) {
				ResultBean.error(MxsConstants.CODE1, "用户名已经存在");
			}
			userDto.setCreateDate(new Date());
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			//添加用户信息
			user = userService.add(user);
			//添加用户权限信息
			userService.addUserPermissions(user.getId(), userDto.getPids());
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResultBean.error();
		}
	    
		return ResultBean.ok();
	}
	/**
	 * 删除用户信息同时，删除用户权限表中的信息
	 * @param uid
	 * @return
	 */
	@GetMapping("/delete")
	public ResultBean deleteUser(Integer uid) {
		boolean flag = userService.delete(uid);
		if(flag){
			return ResultBean.ok();
		}else {
			return ResultBean.error();
		}
	}
}
