package com.mixiusi.repository.read;

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionReadRepository extends JpaRepository<UserPermission, Integer>{
	@Query("from UserPermission up where up.user.id = ?1 and up.permission.id = ?2")
	UserPermission findByUidAndPid(Integer uid, Integer pid);
	
	@Query("select up.permission from UserPermission up where up.user.id = ?")
	List<Permission> findAllPermissionByUid(Integer uid);
	
	@Query("delete from UserPermission up where up.user.id = ?")
	void deleteByUid(Integer uid);
	@Query("delete from UserPermission up where up.permission.id = ?")
	void deleteByPid(Integer pid);
}
