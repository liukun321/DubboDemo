package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.CoffeeMachine;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, String>, JpaSpecificationExecutor<CoffeeMachine>{

	List<CoffeeMachine> findByMachineId(String venderName);

	List<CoffeeMachine> findByEmployeeCode(String employeeCode);

	List<CoffeeMachine> findByEmployeeCodeIsNull();

	CoffeeMachine findByMachineCode(String machineCode);

}
