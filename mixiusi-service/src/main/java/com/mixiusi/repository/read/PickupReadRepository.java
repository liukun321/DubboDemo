package com.mixiusi.repository.read;

import com.mixiusi.bean.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupReadRepository extends JpaRepository<Pickup, String>, JpaSpecificationExecutor<Pickup>{

	Pickup findByPickupCodeAndIsUse(String pkCode, Boolean flag);

}
