package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.Pickup;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, String>, JpaSpecificationExecutor<Pickup>{

	Pickup findByPickupCodeAndIsUse(String pkCode, Boolean flag);

}
