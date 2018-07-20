package com.mixiusi.repository.write;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.CoffeeMaterial;

@Repository
public interface CoffeeMaterialRepository extends JpaRepository<CoffeeMaterial, Integer>, JpaSpecificationExecutor<CoffeeMaterial>{

	List<CoffeeMaterial> findByMachineId(String machineId);

	CoffeeMaterial findByMachineIdAndStackNumber(String machineId, Integer number);

	List<CoffeeMaterial> findByMachineIdAndStatus(String machineId, Integer status);

	List<CoffeeMaterial> findByMachineIdAndStatusAndDangerTimeBetween(String machineId, Integer status,
			Date firstDayOfMonth, Date date);

}
