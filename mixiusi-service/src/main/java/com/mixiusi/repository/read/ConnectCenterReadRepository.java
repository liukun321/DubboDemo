package com.mixiusi.repository.read;

import com.mixiusi.bean.ConnectCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectCenterReadRepository extends JpaRepository<ConnectCenter, Integer> {

}
