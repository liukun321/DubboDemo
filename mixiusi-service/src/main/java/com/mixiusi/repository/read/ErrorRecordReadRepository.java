package com.mixiusi.repository.read;

import com.mixiusi.bean.ErrorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorRecordReadRepository extends JpaRepository<ErrorRecord, Integer>, JpaSpecificationExecutor<ErrorRecord> {

	List<ErrorRecord> findByMachineIdAndEndTimeIsNull(String machineId);

	List<ErrorRecord> findByMachineId(String machineId);
	
	List<ErrorRecord> findByWorkerId(String workerId);

	List<ErrorRecord> findByMachineIdAndType(String machineId, Integer type);

}
