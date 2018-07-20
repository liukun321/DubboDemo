package com.mixiusi.service;

import java.util.List;

import com.mixiusi.bean.ConnectCenter;
/**
 * 运维管理中心联系信息
 * @author liukun
 *
 */
public interface ConnectCenterService {
	/**
	 * 查询所有运维联系人信息
	 * @return
	 */
	List<ConnectCenter> queryAllConnection();
}
