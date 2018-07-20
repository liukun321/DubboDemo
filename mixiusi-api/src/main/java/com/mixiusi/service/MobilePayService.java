package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.bean.vo.MobilePayVo;

/**
 * 微信端订单service
 * @author liukun
 *
 */
public interface MobilePayService {
	//分页查询微信订单
	Double querySumprice();
	//查询用户消费总金额
	Double queryUserSumprice(String openId);
	
	MobilePay queryMobilePayById(String indentId);

	Long querySumNumber(MobilePayVo orderVo);

	List<MobilePay> queryMobilePay(MobilePayVo mvo);

	void updateOrder(MobilePay mp);

	boolean removeMobilePay(List<String> indentIds);

	List<MobilePay> queryMobilePayDetail(MobilePayDetailVo mpdvo);

	Long queryDetailSumNumber(MobilePayDetailVo mpdvo);
	/**
	 * 添加微信端订单
	 * @param pay
	 */
	void add(MobilePay pay);
}
