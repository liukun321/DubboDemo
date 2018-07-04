package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.vo.CoffeeVo;

public interface CoffeeInfoService {
	//查询咖啡信息
	Page<CoffeeInfo> queryAllCoffeeInfo(Integer page, Integer size);
	//查询咖啡的价格
//	Double queryCoffeeInfoForPrice(Integer coffeeId);
	//添加咖啡的品种
	CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo);
	
	//删除咖啡信息
	boolean removeCoffeeInfo(List<String> coffeeIds);
	CoffeeInfo queryCoffeeInfoById(String coffeeId);
	Long queryCoffeeInfoNumber();
	/**
	 * 查询咖啡产品和数量
	 * @param cvo
	 * @return
	 */
	@Reference(cache="lru")
	Long queryCoffeeInfoSum(CoffeeVo cvo);
	@Reference(cache="lru")
	List<CoffeeInfo> queryCoffeeInfo(CoffeeVo cvo);
	
}
