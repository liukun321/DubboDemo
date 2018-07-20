package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.CoffeeMachineAddress;

@Repository
public interface CoffeeMachineAddressRepository extends JpaRepository<CoffeeMachineAddress, String> {
	
}
