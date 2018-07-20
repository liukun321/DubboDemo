package com.mixiusi.biz;

import com.mixiusi.bean.AppVersion;
import com.mixiusi.repository.read.AppVersionReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class AppVersionBiz{
	@Autowired
	private AppVersionReadRepository appVersionReadRepository;
	
	public AppVersion queryAppVersion() {
		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "id"); 
		return appVersionReadRepository.findAll(pageable).getContent().get(0);
	}

}
