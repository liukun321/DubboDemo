package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.ConnectCenter;
import com.mixiusi.biz.ConnectCenterBiz;
import com.mixiusi.repository.write.ConnectCenterRepository;

/**
 * 管理中心联系方式列表
 * @author liukun
 *
 */
@Service(interfaceClass = ConnectCenterService.class)
public class ConnectCenterServiceImpl implements ConnectCenterService {
	@Autowired
	private ConnectCenterBiz connectCenterBiz;

	@Override
	public List<ConnectCenter> queryAllConnection() {
		return connectCenterBiz.queryAllConnection();
	}

}
