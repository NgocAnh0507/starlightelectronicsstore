package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	/*
	 * @Query("SELECT product FROM Product AS product WHERE productID = :prID")
	 * Product findProductByProductId(@Param("prID") Long productID);
	 */
}
