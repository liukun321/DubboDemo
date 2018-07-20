package com.mixiusi.repository.read;

import com.mixiusi.bean.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeMachineReadRepository extends JpaRepository<CoffeeMachine, String>, JpaSpecificationExecutor<CoffeeMachine>{

	List<CoffeeMachine> findByMachineId(String venderName);

	List<CoffeeMachine> findByEmployeeCode(String employeeCode);

	List<CoffeeMachine> findByEmployeeCodeIsNull();

	CoffeeMachine findByMachineCode(String machineCode);

}
