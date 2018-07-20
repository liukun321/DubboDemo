package com.mixiusi.repository.read;

import com.mixiusi.bean.CoffeeMachineAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeMachineAddressReadRepository extends JpaRepository<CoffeeMachineAddress, String> {
	
}
