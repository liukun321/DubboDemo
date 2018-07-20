package com.mixiusi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.AppVersion;
import com.mixiusi.biz.AppVersionBiz;

@Service(interfaceClass = AppVersionService.class, timeout = 5000, cluster = "failover", retries=2, loadbalance="roundrobin")
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private AppVersionBiz appVersionBiz;

	@Override
	public AppVersion queryAppVersion() {
		return appVersionBiz.queryAppVersion();
	}

}
