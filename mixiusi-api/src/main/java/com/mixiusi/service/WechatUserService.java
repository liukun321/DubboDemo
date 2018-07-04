package com.mixiusi.service;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.vo.WechatUserVo;
/**
 * 微信用户service
 * @author liukun
 *
 */
public interface WechatUserService {
	//查询所有用户
	public Page<WechatUser> findAll(WechatUserVo uvo);
	
	public Long findUserNumber(WechatUserVo uvo);

}
