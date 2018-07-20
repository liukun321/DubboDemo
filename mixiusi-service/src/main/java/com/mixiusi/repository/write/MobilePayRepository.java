package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.MobilePay;

@Repository
public interface MobilePayRepository extends JpaRepository<MobilePay, String>,JpaSpecificationExecutor<MobilePay> {
	@Query("SELECT SUM(p.totalPrice) FROM MobilePay p")
	Double querySumPrice();
	
	@Query("SELECT SUM(p.totalPrice) FROM MobilePay p WHERE p.openId = ?")
	Double queryUserSumPrice(String openId);
}
