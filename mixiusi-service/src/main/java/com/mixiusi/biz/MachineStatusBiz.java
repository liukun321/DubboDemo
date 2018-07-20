package com.mixiusi.biz;

import javax.transaction.Transactional;

import com.mixiusi.repository.read.MachineStatusReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.MachineStatus;
import com.mixiusi.repository.write.MachineStatusRepository;

@Service
public class MachineStatusBiz{
	private final Logger log = LoggerFactory.getLogger(MachineStatusBiz.class);
	@Autowired
	private MachineStatusRepository machineStatusRepository;
	@Autowired
	private MachineStatusReadRepository machineStatusReadRepository;
	
	@Transactional
	public void addMachineStatus(MachineStatus machineStatus) {
		try {
			machineStatusRepository.save(machineStatus);
		}catch(Exception e) {
			log.info(e.getMessage() , e);
		}
	}

}
