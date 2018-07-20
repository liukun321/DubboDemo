package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.CouponsVo;
import com.mixiusi.bean.vo.CouponVo;
import com.mixiusi.biz.CouponsBiz;

@Service(interfaceClass = CouponsService.class)
public class CouponsServiceImpl implements CouponsService {
	@Autowired
	private CouponsBiz couponsBiz;
	
	@Override
	public Coupons queryCouponsByCode(String couponCode) {
		return couponsBiz.queryCouponsByCode(couponCode);
	}

	@Override
	public Boolean addCoupons(CouponsVo couponsVo){
		return couponsBiz.addCoupons(couponsVo);
	}

	@Override
	public Coupons updateCoupons(Coupons coupons) {
		return couponsBiz.updateCoupons(coupons);
	}

	@Override
	public List<Coupons> queryCoupons(CouponVo cvo) {
		return couponsBiz.queryCoupons(cvo).getContent();
	}

	@Override
	public Long queryCouponsNumber(CouponVo cvo) {
		return couponsBiz.queryCouponsNumber(cvo);
	}

	@Override
	public boolean removeCouponsByCode(List<String> couponsCodes) {
		return couponsBiz.removeCouponsByCode(couponsCodes);
	}

}
