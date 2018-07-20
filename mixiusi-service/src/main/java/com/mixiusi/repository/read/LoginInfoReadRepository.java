package com.mixiusi.repository.read;

import com.mixiusi.bean.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoReadRepository extends JpaRepository<LoginInfo, String> {

	LoginInfo findByVenderName(String uid);

}
