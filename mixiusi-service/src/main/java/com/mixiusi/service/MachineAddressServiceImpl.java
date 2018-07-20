package com.mixiusi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.CoffeeMachineAddress;
import com.mixiusi.biz.MachineAddressBiz;

@Service(interfaceClass = MachineAddressService.class)
public class MachineAddressServiceImpl implements MachineAddressService {
	@Autowired
	private MachineAddressBiz machineAddressBiz;


	@Override
	public CoffeeMachineAddress updateMachineAddress(CoffeeMachineAddress machineAddress) {
		return machineAddressBiz.updateMachineAddress(machineAddress);
	}


	@Override
	public CoffeeMachineAddress queryMachineAddressById(String machineId) {
		return machineAddressBiz.queryMachineAddressById(machineId);
	}

}
