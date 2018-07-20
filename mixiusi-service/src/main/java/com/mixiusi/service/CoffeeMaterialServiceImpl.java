package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.vo.CoffeeMaterialVo;
import com.mixiusi.biz.CoffeeMaterialBiz;

@Service(interfaceClass = CoffeeMaterialService.class)
public class CoffeeMaterialServiceImpl implements CoffeeMaterialService{
	@Autowired
	private CoffeeMaterialBiz coffeeMaterialBiz;
	
	@Override
	public void addMaterial(CoffeeMaterial material) {
		coffeeMaterialBiz.addMaterial(material);
	}


	@Override
	public List<CoffeeMaterial> queryMaterialByMachineId(String machineId) {
		return coffeeMaterialBiz.queryMaterialByMachineId(machineId);
	}


	@Override
	public CoffeeMaterial queryMaterialByIdAndNumber(String machineId, Integer number) {
		return coffeeMaterialBiz.queryMaterialByIdAndNumber(machineId, number);
	}


	@Override
	public void batchInsertMaterial(List<CoffeeMaterial> list) {
		coffeeMaterialBiz.batchInsertMaterial(list);
	}


	@Override
	public List<CoffeeMaterial> queryCoffeeMaterial(CoffeeMaterialVo cvo) {
		return coffeeMaterialBiz.queryCoffeeMaterial(cvo).getContent();
	}


	@Override
	public Long queryCoffeeMaterialSum(CoffeeMaterialVo cvo) {
		return coffeeMaterialBiz.queryCoffeeMaterialSum(cvo);
	}


	@Override
	public List<CoffeeMaterial> queryMaterialByStatus(String machineId, Integer status) {
		return coffeeMaterialBiz.queryMaterialByStatus(machineId, status);
	}


	@Override
	public List<CoffeeMaterial> queryMaterialByStatusAndTime(String machineId, Integer status) {
		return coffeeMaterialBiz.queryMaterialByStatusAndTime(machineId, status);
	}

}
