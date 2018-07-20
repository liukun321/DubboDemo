package com.mixiusi.repository.read;

import com.mixiusi.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReadRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{

	User findByUserNameAndPassword(String username, String password);

	User findByUserName(String username);

}
