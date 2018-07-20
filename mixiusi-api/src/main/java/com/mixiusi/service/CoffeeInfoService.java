package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.vo.CoffeeVo;
/**
 * 咖啡机产品信息的操作接口
 * @author liukun
 *
 */
public interface CoffeeInfoService {
	/**
	 * 添加咖啡机品种
	 * @param coffeeInfo
	 * @return
	 */
	CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo);
	
	/**
	 * 删除咖啡信息
	 * @param coffeeIds
	 * @return
	 */
	boolean removeCoffeeInfo(List<String> coffeeIds);
	/**
	 * 根据咖啡机Id查询咖啡产品
	 * @param coffeeId
	 * @return
	 */
	CoffeeInfo queryCoffeeInfoById(String coffeeId);
	/**
	 * 查询咖啡机总数量
	 * @return
	 */
	Long queryCoffeeInfoNumber();
	/**
	 * 多条件分页查询咖啡产品和数量
	 * @param cvo
	 * @return
	 */
	@Reference(cache="lru")
	Long queryCoffeeInfoSum(CoffeeVo cvo);
	@Reference(cache="lru")
	List<CoffeeInfo> queryCoffeeInfo(CoffeeVo cvo);
	/**
	 * 无条件查询咖啡信息
	 * @return
	 */
	List<CoffeeInfo> queryAllCoffeeInfo();
	
}
