package com.mixiusi.service;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.WechatUser;
import com.mixiusi.bean.vo.WechatUserVo;

import java.util.List;

/**
 * 微信用户service
 * @author liukun
 *
 */
public interface WechatUserService {
	//查询所有用户
	public List<WechatUser> findAll(WechatUserVo uvo);
	
	public Long findUserNumber(WechatUserVo uvo);

}
