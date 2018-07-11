package com.mixiusi.repository.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.CoffeeInfo;

@Repository
public interface CoffeeInfoReadRepository extends JpaRepository<CoffeeInfo, String>, JpaSpecificationExecutor<CoffeeInfo>{
	CoffeeInfo findByCoffeeId(String coffeeId);
}
