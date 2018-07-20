package com.mixiusi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Permission;
import com.mixiusi.bean.vo.PermissionVo;
import com.mixiusi.biz.PermissionBiz;

/**
 * Created by yxm on 2016.12.29.
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService{
	private final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
	@Autowired
	private PermissionBiz permissionBiz;
	
	@Override
	public Permission add(Permission permission) {
		return permissionBiz.add(permission);
	}

	@Override
	public List<Permission> findAll(PermissionVo pvo) {
		return permissionBiz.findAll(pvo).getContent();
	}
	@Override
	public void delete(Integer pid) {
		permissionBiz.delete(pid);
	}

	@Override
	public Permission queryPermission(Integer pid) {
		
		return permissionBiz.queryPermission(pid);
	}

	@Override
	public Long findPermissionNumber(PermissionVo pvo) {
		return permissionBiz.findPermissionNumber(pvo);  
	}
	
	
}
