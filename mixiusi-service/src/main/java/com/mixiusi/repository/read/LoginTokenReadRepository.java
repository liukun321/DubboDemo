package com.mixiusi.repository.read;

import com.mixiusi.bean.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoginTokenReadRepository extends JpaRepository<LoginToken, Integer>{

	List<LoginToken> findByFailure(boolean b);

	LoginToken findByWorkerIdAndFailureAndExpireTimeAfter(String workerId, boolean b, Date date);
	
}
