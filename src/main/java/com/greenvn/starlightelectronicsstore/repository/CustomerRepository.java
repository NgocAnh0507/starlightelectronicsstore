package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
	@Query("SELECT e FROM Customer AS e WHERE customerID = :uname")
	Customer findCustomerByCustomerId(@Param("uname") Long customerId );
	Page<Customer>findAll(Pageable pageable);
}
