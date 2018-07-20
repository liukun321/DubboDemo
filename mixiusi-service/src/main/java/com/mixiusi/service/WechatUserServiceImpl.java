package com.mixiusi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.vo.WechatUserVo;
import com.mixiusi.biz.WechatUserBiz;

import java.util.List;

@Service(interfaceClass = WechatUserService.class)
public class WechatUserServiceImpl implements WechatUserService {
	private final Logger log = LoggerFactory.getLogger(WechatUserServiceImpl.class);
	@Autowired
	private WechatUserBiz wechatUserBiz; 

	@Override
	public List<WechatUser> findAll(WechatUserVo uvo) {
		return wechatUserBiz.findAll(uvo).getContent();
	}

	@Override
	public Long findUserNumber(WechatUserVo uvo) {
		return wechatUserBiz.findUserNumber(uvo);
	}

}
