package com.mixiusi.biz;

import java.util.List;

import com.mixiusi.repository.read.ConnectCenterReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.ConnectCenter;
import com.mixiusi.repository.write.ConnectCenterRepository;
/**
 * 管理中心联系方式列表
 * @author liukun
 *
 */
@Transactional
@Service
public class ConnectCenterBiz {
	@Autowired
	private ConnectCenterReadRepository connectCenterReadRepository;
	
	public List<ConnectCenter> queryAllConnection() {
		return connectCenterReadRepository.findAll();
	}

}
