package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.WechatUser;
@Repository
public interface UserCouponsRepository extends JpaRepository<UserCoupons, Integer>, JpaSpecificationExecutor<UserCoupons>{

	UserCoupons findByCid(Coupons coupons);
	
	List<Coupons> findByUid(WechatUser user);

}
