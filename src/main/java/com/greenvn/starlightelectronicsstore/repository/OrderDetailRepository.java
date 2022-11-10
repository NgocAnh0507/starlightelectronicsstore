package com.greenvn.starlightelectronicsstore.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.entities.Product;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
	
	Page<OrderDetail> findAll(Pageable pageable);

	@Query("SELECT od FROM OrderDetail AS od WHERE od.orderID = :odID")
	List<OrderDetail> findOrderDetailByOrderID(@Param("odID") long odID);
	
	@Transactional
	@Modifying
	@Query("DELETE  FROM OrderDetail as o  WHERE o.orderID = :odID")
	void deleteOrderDetailByOrderID(@Param("odID") long odID);
}
