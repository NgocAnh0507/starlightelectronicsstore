package com.greenvn.starlightelectronicsstore.repository;

import java.util.List;

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

	@Query("SELECT pr FROM Product AS pr WHERE pr.productSKU = :prSKU")
	Product findProductBySKU(@Param("prSKU") String productSKU);
	
	@Query("SELECT pr FROM Product AS pr WHERE pr.category.name = :cname")
	Page<Product> findProductByCategoryName(@Param("cname") String categoryName, Pageable pageable);

    @Query("SELECT pr FROM Product AS pr WHERE pr.manufacturer.name = :mname")
    Page<Product> findProductByManufacturerName(@Param("mname") String manufacturerName, Pageable pageable);
    
	@Query("SELECT pr FROM Product pr WHERE pr.productName LIKE %:keyword%"
            + " OR pr.category.name LIKE %:keyword%"
            + " OR pr.manufacturer.name LIKE %:keyword%")
	 Page<Product>search(Pageable pageable,@Param( "keyword") String keyword);
}
