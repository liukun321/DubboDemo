package com.mixiusi.biz;

import javax.transaction.Transactional;

import com.mixiusi.repository.read.MaterialReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.Material;
import com.mixiusi.repository.write.MaterialRepository;
@Transactional
@Service
public class MaterialBiz {
private final Logger log = LoggerFactory.getLogger(MaterialBiz.class);
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialReadRepository materialReadRepository;
	
	public Material updateMaterial(Material material) {
		
		return materialRepository.save(material);
	}


	public Material queryMaterial(String venderName) {
		return materialReadRepository.findByMachineId(venderName);
	}

}
