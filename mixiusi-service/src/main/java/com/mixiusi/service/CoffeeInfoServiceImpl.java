package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.vo.CoffeeVo;
import com.mixiusi.biz.CoffeeInfoBiz;

@Service(interfaceClass = CoffeeInfoService.class, timeout = 5000, version = "1.0.0", cluster = "failover", retries=2, loadbalance="roundrobin")
public class CoffeeInfoServiceImpl implements CoffeeInfoService {
	@Autowired
	private CoffeeInfoBiz coffeeInfoBiz;
	@Override
	public CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo) {
		return coffeeInfoBiz.addCoffeeInfo(coffeeInfo);
	}

	@Override
	public Page<CoffeeInfo> queryAllCoffeeInfo(Integer page, Integer size) {
		return coffeeInfoBiz.queryAllCoffeeInfo(page, size);
	}

	@Override
	public boolean removeCoffeeInfo(List<String> coffeeIds) {
		return coffeeInfoBiz.removeCoffeeInfo(coffeeIds);
	}

	@Override
	public CoffeeInfo queryCoffeeInfoById(String coffeeId) {
		return coffeeInfoBiz.queryCoffeeInfoById(coffeeId);
	}

	@Override
	public Long queryCoffeeInfoNumber() {
		return coffeeInfoBiz.queryCoffeeInfoNumber();
	}

	@Override
	public Long queryCoffeeInfoSum(CoffeeVo cvo) {
		return coffeeInfoBiz.queryCoffeeInfoSum(cvo);
	}

	@Override
	public List<CoffeeInfo> queryCoffeeInfo(CoffeeVo cvo) {
		return coffeeInfoBiz.queryCoffeeInfo(cvo);
	}

}
