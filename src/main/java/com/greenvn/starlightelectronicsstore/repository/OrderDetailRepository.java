package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
	
}
