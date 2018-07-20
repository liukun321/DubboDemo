package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.vo.WechatUserVo;

public interface UserCouponsService {
	
	List<Coupons> queryCoupons(String openId);
	
	UserCoupons queryCouponsById(Coupons coupons);
	//查询用户优惠券信息
	List<UserCoupons> findAllCoupons(WechatUserVo wvo);
	//查询用户优惠券数量
	Long findCouponsNumber(String openId);
}
