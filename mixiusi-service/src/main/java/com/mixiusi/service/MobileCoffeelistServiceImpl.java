package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.vo.MobilePayDetailVo;
import com.mixiusi.biz.MobileCoffeelistBiz;
import com.mixiusi.biz.MobilePayBiz;

@Service(interfaceClass = MobileCoffeelistService.class)
public class MobileCoffeelistServiceImpl implements MobileCoffeelistService {
	@Autowired
	private MobileCoffeelistBiz mobileCoffeelistBiz;
	
	@Autowired
	private MobilePayBiz mobilePayBiz;
	@Override
	public List<MobileCoffeeList> queryCoffeeAll(String indentId) {
		return mobileCoffeelistBiz.queryCoffeeAll(indentId);
	}
	@Override
	public List<MobileCoffeeList> queryMobilePayDetail(MobilePayDetailVo mpdvo) {
		return mobileCoffeelistBiz.queryMobilePayDetail(mpdvo).getContent();
	}
	@Override
	public Long queryDetailSumNumber(MobilePayDetailVo mpdvo) {
		return mobileCoffeelistBiz.queryDetailSumNumber(mpdvo);
	}

}
