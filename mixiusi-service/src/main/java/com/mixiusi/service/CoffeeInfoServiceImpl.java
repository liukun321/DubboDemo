package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.vo.CoffeeVo;
import com.mixiusi.biz.CoffeeInfoBiz;

@Service(interfaceClass = CoffeeInfoService.class, timeout = 5000, version = "1.0.0", cluster = "failover", retries=2, loadbalance="roundrobin")
public class CoffeeInfoServiceImpl implements CoffeeInfoService {
	@Autowired
	private CoffeeInfoBiz coffeeInfoBiz;
	@Reference(version = "1.0.0")
	private RedisService redisService;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CoffeeInfo> queryCoffeeInfo(CoffeeVo cvo) {
		//1 查询缓存中是否存在，存在则从缓存中获取，否则从数据库中获取并在缓存中保存数据
		List<CoffeeInfo> result = (List<CoffeeInfo>) redisService.get(CoffeeInfoService.class.getSimpleName() + "-queryCoffeeInfo");
		if(null == result) {
			result = coffeeInfoBiz.queryCoffeeInfo(cvo);
			redisService.set(CoffeeInfoService.class.getSimpleName() + "queryCoffeeInfo", result);
		}
		return result;
	}

}
