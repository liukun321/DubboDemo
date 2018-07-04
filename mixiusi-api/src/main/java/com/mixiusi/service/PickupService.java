package com.mixiusi.service;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Pickup;
import com.mixiusi.bean.vo.PickupVo;

public interface PickupService {
	
	Pickup addPickup(Pickup pk);

	Page<Pickup> getAllPickup(PickupVo pvo);

	Long getAllPickupNumber(PickupVo pvo);
}
