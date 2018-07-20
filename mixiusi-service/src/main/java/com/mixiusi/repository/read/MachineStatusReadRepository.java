package com.mixiusi.repository.read;

import com.mixiusi.bean.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineStatusReadRepository extends JpaRepository<MachineStatus, String> {

}
