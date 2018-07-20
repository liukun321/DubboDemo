package com.mixiusi.biz;

import com.mixiusi.repository.read.CoffeeMachineAddressReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.CoffeeMachineAddress;
import com.mixiusi.repository.write.CoffeeMachineAddressRepository;
import com.mixiusi.service.MachineAddressService;
@Service
public class MachineAddressBiz {
	@Autowired
	private CoffeeMachineAddressRepository machineAddressRepository;
	@Autowired
	private CoffeeMachineAddressReadRepository machineAddressReadRepository;


	public CoffeeMachineAddress updateMachineAddress(CoffeeMachineAddress machineAddress) {
		return machineAddressRepository.save(machineAddress);
	}


	public CoffeeMachineAddress queryMachineAddressById(String machineId) {
		return machineAddressReadRepository.findOne(machineId);
	}

}
