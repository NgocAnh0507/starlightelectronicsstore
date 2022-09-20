package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

}
