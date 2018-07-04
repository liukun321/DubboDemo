package com.mixiusi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.CoffeeInfo;

@Repository("coffeeInfoRepository")
public interface CoffeeInfoRepository extends JpaRepository<CoffeeInfo, String>, JpaSpecificationExecutor<CoffeeInfo>{

	CoffeeInfo findByCoffeeId(String coffeeId);

}
