package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.ConnectCenter;

@Repository
public interface ConnectCenterRepository extends JpaRepository<ConnectCenter, Integer> {

}
