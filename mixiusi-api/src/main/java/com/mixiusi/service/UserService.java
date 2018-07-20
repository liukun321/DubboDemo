package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.User;
import com.mixiusi.bean.vo.UserVo;
/**
 * mxs管理平台用户service
 * @author liukun
 *
 */
public interface UserService {
	//查询所有用户
	public List<User> findAll(UserVo uvo);
	//添加用户
	public User add(User user);
	//删除用户
	public boolean delete(Integer uid);
	//查询一个用户，根据用户Id
	public User load(Integer uid);
	//根据用户名差匈奴用户信息
	public User findByUserName(String username);
	//添加用户权限
	public void addUserPermissions(Integer uid, List<Integer> pids);
	//根据用户名和密码查询查询信息
	public User findByUserNamePassword(String username, String password);
	//根据用户Id查询用户的权限
	List<Permission> listUserPermissions(Integer uid);
	
	public Long findUserNumber(UserVo uvo);

}
