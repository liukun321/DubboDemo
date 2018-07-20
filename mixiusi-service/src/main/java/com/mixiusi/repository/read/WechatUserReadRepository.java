package com.mixiusi.repository.read;

import com.mixiusi.bean.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserReadRepository extends JpaRepository<WechatUser, String>, JpaSpecificationExecutor<WechatUser>{

}
