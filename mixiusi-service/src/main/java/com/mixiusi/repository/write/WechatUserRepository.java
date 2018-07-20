package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.WechatUser;
@Repository
public interface WechatUserRepository extends JpaRepository<WechatUser, String>, JpaSpecificationExecutor<WechatUser>{

}
