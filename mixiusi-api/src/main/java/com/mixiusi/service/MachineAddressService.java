package com.mixiusi.service;

import com.mixiusi.bean.CoffeeMachineAddress;

public interface MachineAddressService {
	//保存和更新咖啡机地理位置
//	public CoffeeMachineAddress updateMachineAddress(CoffeeMachineAddress machineAddress);
	
	public CoffeeMachineAddress queryMachineAddressById(String machineId);
	
	
}
