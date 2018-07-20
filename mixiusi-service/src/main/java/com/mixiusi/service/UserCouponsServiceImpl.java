package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.vo.WechatUserVo;
import com.mixiusi.biz.UserCouponsBiz;

@Service(interfaceClass = UserCouponsService.class)
public class UserCouponsServiceImpl implements UserCouponsService {
	
	@Autowired
	private UserCouponsBiz userCouponsBiz;

	@Override
	public List<Coupons> queryCoupons(String openId) {
		return userCouponsBiz.queryCoupons(openId);
	}


	@Override
	public UserCoupons queryCouponsById(Coupons coupons) {
		return userCouponsBiz.queryCouponsById(coupons);
	}

	@Override
	public List<UserCoupons> findAllCoupons(WechatUserVo wvo) {
		return userCouponsBiz.findAllCoupons(wvo).getContent();
	}


	@Override
	public Long findCouponsNumber(String openId) {
		return userCouponsBiz.findCouponsNumber(openId);
	}

}
