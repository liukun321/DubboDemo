package com.mixiusi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.vo.CoffeeVo;
import com.mixiusi.biz.CoffeeInfoBiz;

@Service(interfaceClass = CoffeeInfoService.class, timeout = 5000, cluster = "failover", loadbalance="roundrobin")
public class CoffeeInfoServiceImpl implements CoffeeInfoService {
	private final Logger log = LoggerFactory.getLogger(CoffeeInfoServiceImpl.class);
	@Autowired
	private CoffeeInfoBiz coffeeInfoBiz;
	@Reference(version = "1.0.0")
	private RedisService redisService;
	@Override
	@CachePut
	public CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo) {
		return coffeeInfoBiz.addCoffeeInfo(coffeeInfo);
	}

//	@Override
//	public List<CoffeeInfo> queryAllCoffeeInfo(Integer page, Integer size) {
//		return coffeeInfoBiz.queryAllCoffeeInfo(page, size).getContent();
//	}

	@Override
	@CacheEvict()
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
			redisService.set(CoffeeInfoService.class.getSimpleName() + "-queryCoffeeInfo", result);
		}
		log.info("1232" + result);
		return result;
	}

	@Override
	public List<CoffeeInfo> queryAllCoffeeInfo() {
		return coffeeInfoBiz.queryAll();
	}
}
