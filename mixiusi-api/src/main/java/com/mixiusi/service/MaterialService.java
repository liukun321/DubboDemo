package com.mixiusi.service;

import com.mixiusi.bean.Material;

public interface MaterialService {
	Material updateMaterial(Material material);
	
	Material queryMaterial(String venderName);
	
//	Page<Material> queryAll(Integer page, Integer size);
//
//	Page<Material> queryAllCriteria(Integer page, Integer size, String machineId);
}
