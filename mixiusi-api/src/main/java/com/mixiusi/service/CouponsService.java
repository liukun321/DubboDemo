package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.CouponsVo;
import com.mixiusi.bean.vo.CouponVo;
/**
 * 优惠券接口
 * @author liukun
 *
 */
public interface CouponsService {
	/**
	 * 根据优惠码查询优惠券
	 * @param couponCode
	 * @return
	 */
	Coupons queryCouponsByCode(String couponCode);
	/**
	 * 添加优惠券
	 * @param coupons
	 * @return
	 */
	Boolean addCoupons(CouponsVo couponsVo);
	/**
	 * 更新优惠券状态
	 * @param coupons
	 * @return
	 */
	Coupons updateCoupons(Coupons coupons);
	/**
	 * 多条件分页查询
	 * @param cvo
	 * @return
	 */
	List<Coupons> queryCoupons(CouponVo cvo);
	/**
	 * 多条件查询总数量
	 * @param cvo
	 * @return
	 */
	Long queryCouponsNumber(CouponVo cvo);
	/**
	 * 批量删除
	 * @param couponsCodes
	 * @return
	 */
	boolean removeCouponsByCode(List<String> couponsCodes);
}
