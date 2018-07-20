package com.mixiusi.repository.write;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mixiusi.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

	Employee findByPhoneNumber(String phoneNumber);

	Employee findByPhoneNumberAndPassword(String tel, String password);

}
