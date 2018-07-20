package com.mixiusi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.MachineStatus;
import com.mixiusi.biz.MachineStatusBiz;

@Service(interfaceClass = MachineStatusService.class, timeout = 5000)
public class MachineStatusServiceImpl implements MachineStatusService {
	private final Logger log = LoggerFactory.getLogger(MachineStatusServiceImpl.class);
	@Autowired
	private MachineStatusBiz machineStatusBiz;
	
	public void addMachineStatus(MachineStatus machineStatus) {
		machineStatusBiz.addMachineStatus(machineStatus);
	}

}
