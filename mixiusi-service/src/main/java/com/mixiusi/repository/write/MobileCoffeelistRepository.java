package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.MobileCoffeeList;

@Repository
public interface MobileCoffeelistRepository extends JpaRepository<MobileCoffeeList, Integer>, JpaSpecificationExecutor<MobileCoffeeList>{

	List<MobileCoffeeList> findByMid(String indentId);
	
}
