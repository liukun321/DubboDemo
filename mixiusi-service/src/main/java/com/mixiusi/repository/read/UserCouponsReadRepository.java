package com.mixiusi.repository.read;

import com.mixiusi.bean.Coupons;
import com.mixiusi.bean.UserCoupons;
import com.mixiusi.bean.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponsReadRepository extends JpaRepository<UserCoupons, Integer>, JpaSpecificationExecutor<UserCoupons>{

	UserCoupons findByCid(Coupons coupons);
	
	List<Coupons> findByUid(WechatUser user);

}
