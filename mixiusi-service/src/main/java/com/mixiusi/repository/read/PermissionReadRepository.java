package com.mixiusi.repository.read;

import com.mixiusi.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionReadRepository extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor<Permission>{

}
