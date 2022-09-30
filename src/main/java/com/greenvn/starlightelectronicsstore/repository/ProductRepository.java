package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.greenvn.starlightelectronicsstore.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	Page<Product> findAll(Pageable pageable);
	
	@Query("SELECT pr FROM Product AS pr WHERE pr.productName = :prname")
	Product findProductByName(@Param("prname") String productName);
}
