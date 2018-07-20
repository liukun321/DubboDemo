package com.mixiusi.repository.read;

import com.mixiusi.bean.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialReadRepository extends JpaRepository<Material, String> {

	Material findByMachineId(String venderName);

}
