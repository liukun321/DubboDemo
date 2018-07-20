package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.EmployeeMachines;

@Repository
public interface EmployeeMachinesRepository extends JpaRepository<EmployeeMachines, String> {
	List<EmployeeMachines> findByWorkerId(String workerId);

	EmployeeMachines findByMachineId(String machineId);
}
