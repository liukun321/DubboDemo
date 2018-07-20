package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, String> {

	LoginInfo findByVenderName(String uid);

}
