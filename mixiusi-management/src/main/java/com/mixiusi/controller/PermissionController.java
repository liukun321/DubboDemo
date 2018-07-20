package com.mixiusi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.Permission;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.vo.PermissionVo;
import com.mixiusi.config.ApplicationProperties;
import com.mixiusi.service.PermissionService;


/**
 * 用户权限controller
 * @author liukun
 *
 */
@RestController
@RequestMapping(value = "/admin/permission")
public class PermissionController {
	private static Logger log = LoggerFactory.getLogger(PermissionController.class);
	@Reference
    private PermissionService permissionService;
    
	@Autowired
	private ApplicationProperties application;
    
    @GetMapping("/queryAll")
    public ResultBean queryAll(PermissionVo pvo) {
    	Integer page = pvo.getPage();
		Integer size = pvo.getSize();
		List<Permission> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
    	list = permissionService.findAll(pvo);
    	Long sum = permissionService.findPermissionNumber(pvo);
    	if(null == list)
    		return ResultBean.error();
    	return ResultBean.ok(size, page, sum, list);
    }
    
}
