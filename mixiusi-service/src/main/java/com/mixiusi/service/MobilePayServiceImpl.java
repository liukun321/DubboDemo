package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.MobilePay;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.bean.vo.MobilePayVo;
import com.mixiusi.biz.MobilePayBiz;

@Service(interfaceClass = MobilePayService.class)
public class MobilePayServiceImpl implements MobilePayService {
	@Autowired
	private MobilePayBiz mobilePayBiz;

	@Override
	public void add(MobilePay pay) {
		mobilePayBiz.add(pay);
		
	}

	@Override
	public Double querySumprice() {
		return mobilePayBiz.querySumprice();
	}

	@Override
	public Double queryUserSumprice(String openId) {
		return mobilePayBiz.queryUserSumprice(openId);
	}

	@Override
	public MobilePay queryMobilePayById(String indentId) {
		return mobilePayBiz.queryMobilePay(indentId);
	}

	@Override
	public Long querySumNumber(MobilePayVo orderVo) {
		return mobilePayBiz.querySumNumber(orderVo);
	}

	@Override
	public List<MobilePay> queryMobilePay(MobilePayVo mvo) {
		return mobilePayBiz.queryMobilePay(mvo).getContent();
	}

	@Override
	public void updateOrder(MobilePay mp) {
		mobilePayBiz.updateOrder(mp);
		
	}

	@Override
	public boolean removeMobilePay(List<String> indentIds) {
		return mobilePayBiz.removeMobilePay(indentIds);
	}

	@Override
	public List<MobilePay> queryMobilePayDetail(MobilePayDetailVo mpdvo) {
		return mobilePayBiz.queryMobilePayDetail(mpdvo).getContent();
	}

	@Override
	public Long queryDetailSumNumber(MobilePayDetailVo mpdvo) {
		return mobilePayBiz.queryDetailSumNumber(mpdvo);
	}


}
