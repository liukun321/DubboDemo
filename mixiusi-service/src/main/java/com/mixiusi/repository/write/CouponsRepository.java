package com.mixiusi.repository.write;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.Coupons;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer> ,JpaSpecificationExecutor<Coupons>{
//	@Query("select * from Coupons c where c.couponCode=?1 and c.endTime > ?2")
	Coupons findByCouponCodeAndEndTimeAfter(String couponCode, Date date);

	Coupons findByCouponCode(String id);

}
