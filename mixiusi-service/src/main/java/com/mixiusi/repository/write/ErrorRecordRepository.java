package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.ErrorRecord;

@Repository
public interface ErrorRecordRepository extends JpaRepository<ErrorRecord, Integer>, JpaSpecificationExecutor<ErrorRecord> {

	List<ErrorRecord> findByMachineIdAndEndTimeIsNull(String machineId);

	List<ErrorRecord> findByMachineId(String machineId);
	
	List<ErrorRecord> findByWorkerId(String workerId);

	List<ErrorRecord> findByMachineIdAndType(String machineId, Integer type);

}
