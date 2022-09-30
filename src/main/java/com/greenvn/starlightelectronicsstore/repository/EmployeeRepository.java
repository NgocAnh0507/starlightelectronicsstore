package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
	Page<Employee> findAll(Pageable pageable);
	
	@Query("SELECT e FROM Employee AS e WHERE e.userName = :uname")
	Employee findEmployeeByUserName(@Param("uname") String userName);
}
