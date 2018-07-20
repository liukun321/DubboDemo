package com.mixiusi.repository.read;

import com.mixiusi.bean.MobileCoffeeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileCoffeelistReadRepository extends JpaRepository<MobileCoffeeList, Integer>, JpaSpecificationExecutor<MobileCoffeeList>{

	List<MobileCoffeeList> findByMid(String indentId);
	
}
