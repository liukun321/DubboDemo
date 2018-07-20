package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Pickup;
import com.mixiusi.bean.vo.PickupVo;

public interface PickupService {
	/**
	 * 添加取货信息
	 * @param pk
	 * @return
	 */
	Pickup addPickup(Pickup pk);
	/**
	 * 分页查询取货信息
	 * @param pvo
	 * @return
	 */
	List<Pickup> getAllPickup(PickupVo pvo);
	/**
	 * 查询取货信息数量
	 * @param pvo
	 * @return
	 */
	Long getAllPickupNumber(PickupVo pvo);
	/**
	 * 根据取货码查询取货信息
	 * @param pkCode
	 * @return
	 */
	Pickup queryPickup(String pkCode);
	/**
	 * 根据订单Id查询取货信息
	 * @param indentId
	 * @return
	 */
	Pickup queryPickupById(String indentId);
	/**
	 * 批量更新取货状态
	 * @param pk
	 */
	void updateStatus(Pickup pk);
	
	/**
	 * 批量删除取货吗信息
	 * @param indentIds
	 * @return
	 */
	Boolean removePickup(List<String> indentIds);
}
