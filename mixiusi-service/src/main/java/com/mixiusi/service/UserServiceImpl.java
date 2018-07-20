package com.mixiusi.service;

import java.util.List;

import com.mixiusi.biz.UserServiceBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Permission;
import com.mixiusi.bean.User;
import com.mixiusi.bean.vo.UserVo;
/**
 * 
 * @author liukun
 *
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserServiceBiz userServiceBiz;

	@Override
	public List<User> findAll(UserVo uvo) {
		return userServiceBiz.findAll(uvo).getContent();
	}


	@Override
	public User add(User user) {
    	return userServiceBiz.add(user);
	}

	@Override
	public boolean delete(Integer uid) {
		return userServiceBiz.delete(uid);
	}


	@Override
	public User load(Integer uid) {
		 return userServiceBiz.load(uid);
	}


	@Override
	public User findByUserName(String username) {
		 return userServiceBiz.findByUserName(username);
	}

	@Override
	public void addUserPermissions(Integer uid, List<Integer> pids) {
		userServiceBiz.addUserPermissions(uid, pids);		
	}

	@Override
	public User findByUserNamePassword(String username, String password) {
		return userServiceBiz.findByUserNamePassword(username, password);
	}


	@Override
	public List<Permission> listUserPermissions(Integer uid) {
		return userServiceBiz.listUserPermissions(uid);
	}

	@Override
	public Long findUserNumber(UserVo uvo) {
		return userServiceBiz.findUserNumber(uvo);
	}
}
