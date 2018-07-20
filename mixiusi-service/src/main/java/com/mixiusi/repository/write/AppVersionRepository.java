package com.mixiusi.repository.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.AppVersion;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, Integer>{
//	@Query("SELECT a.version, a.title, a.content, a.downloadUrl,MAX(a.id) FROM AppVersion a GROUP BY id")
//	AppVersion findByMaxVersion();
	@Query("FROM AppVersion a ORDER BY id DESC")
	List<AppVersion> findAllApp();
	
}
