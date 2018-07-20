package com.mixiusi.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {

	Material findByMachineId(String venderName);

}
