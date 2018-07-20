package com.mixiusi.repository.read;

import com.mixiusi.bean.Payindent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayindentReadRepository extends JpaRepository<Payindent, String>, JpaSpecificationExecutor<Payindent> {
	Payindent findByIndentId(String indentId);

	List<Payindent> findByPayStatus(Integer status);
	@Query("SELECT SUM(p.price) FROM Payindent p WHERE p.payStatus=?1 and p.payMethod = ?2")
	Double querySumprice(Integer status, Integer payMethod);

}
