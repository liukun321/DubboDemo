package com.mixiusi.repository.read;

import com.mixiusi.bean.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppVersionReadRepository extends JpaRepository<AppVersion, Integer>{
//	@Query("SELECT a.version, a.title, a.content, a.downloadUrl,MAX(a.id) FROM AppVersion a GROUP BY id")
//	AppVersion findByMaxVersion();
	@Query("FROM AppVersion a ORDER BY id DESC")
	List<AppVersion> findAllApp();
	
}
