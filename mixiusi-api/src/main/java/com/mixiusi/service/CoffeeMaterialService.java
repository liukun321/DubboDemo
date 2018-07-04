package com.mixiusi.service;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.vo.CoffeeMaterialVo;

public interface CoffeeMaterialService {
	public void addMaterial(CoffeeMaterial material);
	/**
	 * 查询咖啡机物料信息
	 * @param cvo
	 * @return
	 */
	public Page<CoffeeMaterial> queryCoffeeMaterial(CoffeeMaterialVo cvo);
	//查询总数
	public Long queryCoffeeMaterialSum(CoffeeMaterialVo cvo);
}
