package com.mixiusi.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeInfo;
import com.mixiusi.bean.ResultBean;
import com.mixiusi.bean.utils.GenerateUniqueId;
import com.mixiusi.bean.utils.MxsConstants;
import com.mixiusi.bean.vo.CoffeeVo;
import com.mixiusi.service.CoffeeInfoService;
import com.mixiusi.service.PayindentService;
import com.mixiusi.vo.CoffeeInfoVo;
import com.mixiusi.vo.CoffeeSale;

@RestController
@RequestMapping("/coffeeInfo")
public class CoffeeInfoController {
	private final Logger log = LoggerFactory.getLogger(CoffeeInfoController.class);
	@Reference
	private CoffeeInfoService coffeeInfoService;
	@Reference
	private PayindentService payindentService;
	/**
	 * 添加咖啡产品
	 * @param coffeeInfoVo
	 * @return
	 */
	@PostMapping("/addCoffee")
	public ResultBean addCoffeeInfo(CoffeeInfoVo coffeeInfoVo) {
		CoffeeInfo coffeeInfo = new CoffeeInfo();
		BeanUtils.copyProperties(coffeeInfoVo, coffeeInfo);
		String coffeeId = GenerateUniqueId.generateCoffeeId();
		coffeeInfo.setCoffeeId(coffeeId);
		CoffeeInfo c = coffeeInfoService.addCoffeeInfo(coffeeInfo);
		if(null ==  c) {
			return ResultBean.error(MxsConstants.CODE1, "Add coffeeInfo failure");
		}
		return ResultBean.ok();
	}
	
	@PostMapping("/updateCoffee")
	public ResultBean updateCoffee(CoffeeInfoVo coffeeInfoVo) {
		boolean flag = true;
		CoffeeInfo coffeeInfo = coffeeInfoService.queryCoffeeInfoById(coffeeInfoVo.getCoffeeId());
		if(null == coffeeInfo) {
			flag = false;
		}else{
			BeanUtils.copyProperties(coffeeInfoVo, coffeeInfo);
			CoffeeInfo c = coffeeInfoService.addCoffeeInfo(coffeeInfo);
			if(null == c)
				flag = false;
		}
		if(!flag) {
			return ResultBean.error(MxsConstants.CODE1, "Update coffeeInfo failure");
		}
		return ResultBean.ok();
	}
	
	
	/**
	 * 查询所有咖啡信息
	 * @return
	 */
	@GetMapping("/getAll")
	public ResultBean getCoffeeInfo(CoffeeVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		List<CoffeeInfo> list = null;
		if(null == page || null == size) {
			return ResultBean.error(MxsConstants.CODE1, "page or size is null");
		}
		if(size < 1) {
			return ResultBean.error(MxsConstants.CODE1, "Page size must not be less than one");
		}
		list = coffeeInfoService.queryCoffeeInfo(cvo);
		Long sum = coffeeInfoService.queryCoffeeInfoSum(cvo);
		List<CoffeeSale> resultList = new ArrayList<>();
		for (CoffeeInfo coffeeInfo : list) {
			CoffeeSale coffeeSale = new CoffeeSale();
			Long number = payindentService.queryCoffeeSaleSum(coffeeInfo.getCoffeeId());
			coffeeSale.setSaleSum(number);
			BeanUtils.copyProperties(coffeeInfo, coffeeSale);
			resultList.add(coffeeSale);
		}
		return ResultBean.ok(size, page, sum, resultList);
	}
	
	/**
	 * 删除咖啡信息
	 * @param coffeeIds
	 * @return
	 */
	@GetMapping("/deleteCoffee")
	public ResultBean removeCoffeeInfo(List<String> coffeeIds) {
		boolean flag = coffeeInfoService.removeCoffeeInfo(coffeeIds);
		if(!flag) {
			return ResultBean.error();
		}
		return ResultBean.ok();
	}
	
}
