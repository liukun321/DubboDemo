package com.mixiusi.repository.write;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.LoginToken;

@Repository
public interface LoginTokenRepository extends JpaRepository<LoginToken, Integer>{

	List<LoginToken> findByFailure(boolean b);

	LoginToken findByWorkerIdAndFailureAndExpireTimeAfter(String workerId, boolean b, Date date);
	
}
