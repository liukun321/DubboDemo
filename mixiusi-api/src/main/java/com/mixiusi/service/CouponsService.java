package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.vo.CouponVo;

public interface CouponsService {
	Coupons queryCouponsByCode(String couponCode);
	
	Coupons addCoupons(Coupons coupons);
	
	Coupons updateCoupons(Coupons coupons);
	
	void removeCoupons(Coupons coupons);
	
	Page<Coupons> getAll(Integer page, Integer size);
	
	Page<Coupons> queryCoupons(CouponVo cvo);
	
	Long queryCouponsNumber(CouponVo cvo);

	boolean removeCouponsByCode(List<String> couponsCodes);
}
