package com.mixiusi.repository.read;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.EmployeeMachines;

@Repository
public interface EmployeeMachinesReadRepository extends JpaRepository<EmployeeMachines, String>,JpaSpecificationExecutor<EmployeeMachines> {

	List<EmployeeMachines> findByWorkerId(String workerId);

	EmployeeMachines findByMachineId(String machineId);

}
