package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.vo.CoffeeMaterialVo;
/**
 * 咖啡机物料信息操作接口
 * @author liukun
 *
 */
public interface CoffeeMaterialService {
	/**
	 * 添加咖啡机物料信息
	 * @param material
	 */
	public void addMaterial(CoffeeMaterial material);
	/**
	 * 查询咖啡机物料信息
	 * @param cvo
	 * @return
	 */
	public List<CoffeeMaterial> queryCoffeeMaterial(CoffeeMaterialVo cvo);
	//查询总数
	public Long queryCoffeeMaterialSum(CoffeeMaterialVo cvo);
	
	/**
	 * 根据 咖啡机Id和物料盒的编号查询指定的料盒信息
	 * @param machineId
	 * @param number
	 * @return
	 */
	public CoffeeMaterial queryMaterialByIdAndNumber(String machineId, Integer number);
	
	/**
	 * 批量插入
	 * @param list
	 */
	public void batchInsertMaterial(List<CoffeeMaterial> list);
	
	public List<CoffeeMaterial> queryMaterialByMachineId(String machineId);
	
	public List<CoffeeMaterial> queryMaterialByStatus(String machineId, Integer status);
	
	public List<CoffeeMaterial> queryMaterialByStatusAndTime(String machineId, Integer status);
}
