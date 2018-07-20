package com.mixiusi.repository.read;

import com.mixiusi.bean.CoffeeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CoffeeMaterialReadRepository extends JpaRepository<CoffeeMaterial, Integer>, JpaSpecificationExecutor<CoffeeMaterial>{

	List<CoffeeMaterial> findByMachineId(String machineId);

	CoffeeMaterial findByMachineIdAndStackNumber(String machineId, Integer number);

	List<CoffeeMaterial> findByMachineIdAndStatus(String machineId, Integer status);

	List<CoffeeMaterial> findByMachineIdAndStatusAndDangerTimeBetween(String machineId, Integer status,
                                                                      Date firstDayOfMonth, Date date);

}
