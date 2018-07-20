package com.mixiusi.service;

import com.mixiusi.bean.AppVersion;
/**
 * 机器端APP版本更新检测
 * @author liukun
 *
 */
public interface AppVersionService {
	/**
	 * 查询APP最新的版本
	 * @return
	 */
	AppVersion queryAppVersion();
}
