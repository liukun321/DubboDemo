package com.mixiusi.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Pickup;
import com.mixiusi.bean.vo.PickupVo;
import com.mixiusi.biz.PickupBiz;

@Service(interfaceClass = PickupService.class)
public class PickupServiceImpl implements PickupService {
	@Autowired
	private PickupBiz pickupBiz;

	@Override
	public Pickup queryPickup(String pkCode) {
		return pickupBiz.queryPickup(pkCode);
	}

	@Override
	public Pickup queryPickupById(String indentId) {
		return pickupBiz.queryPickupById(indentId);
	}

	@Override
	public void updateStatus(Pickup pk) {
		pickupBiz.updateStatus(pk);
		
	}

	@Override
	public Pickup addPickup(Pickup pk) {
		return pickupBiz.addPickup(pk);
	}

	@Override
	public List<Pickup> getAllPickup(PickupVo pvo) {
		return pickupBiz.getAllPickup(pvo).getContent();
	}

	@Override
	public Long getAllPickupNumber(PickupVo pvo) {
		return pickupBiz.getAllPickupNumber(pvo);
	}

	@Override
	public Boolean removePickup(List<String> indentIds) {
		return pickupBiz.removePickup(indentIds);
	}
}
