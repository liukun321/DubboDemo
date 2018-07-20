package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mixiusi.bean.MachineStatus;

@Repository
public interface MachineStatusRepository extends JpaRepository<MachineStatus, String> {

}
