package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.vo.PermissionVo;

public interface PermissionService {
	//添加权限
	public Permission add(Permission permission);
	//分页查询权限
	public List<Permission> findAll(PermissionVo pvo);
	//删除权限
	public void delete(Integer pid);
	//根据权限ID查询权限
	public Permission queryPermission(Integer pid);
	
	Long findPermissionNumber(PermissionVo pvo);
}
