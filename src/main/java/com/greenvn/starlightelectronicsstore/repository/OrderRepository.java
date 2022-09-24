package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
	/*
	 * @Query("SELECT order FROM Order AS order WHERE orderID = :orID") Order
	 * findOrderByOrderId(@Param("orID") Long orderID);
	 */
}
