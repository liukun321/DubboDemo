package com.mixiusi.repository.read;


import com.mixiusi.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReadRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	Employee findByPhoneNumber(String phoneNumber);

	Employee findByPhoneNumberAndPassword(String tel, String password);

}
