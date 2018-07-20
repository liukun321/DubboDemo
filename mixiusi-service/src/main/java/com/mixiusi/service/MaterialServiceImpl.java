package com.mixiusi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Material;
import com.mixiusi.biz.MaterialBiz;

@Service(interfaceClass = MaterialService.class)
public class MaterialServiceImpl implements MaterialService {
private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);
	@Autowired
	private MaterialBiz materialBiz;
	
	@Override
	public Material updateMaterial(Material material) {
		
		return materialBiz.updateMaterial(material);
	}


	@Override
	public Material queryMaterial(String venderName) {
		return materialBiz.queryMaterial(venderName);
	}

}
