package com.greenvn.starlightelectronicsstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long>{

	Page<ProductAttribute> findAll(Pageable pageable);
	
	@Query("SELECT a FROM ProductAttribute AS a WHERE a.category.categoryID = :cID")
	List<ProductAttribute> findProductAttributeByCategoryID(@Param("cID") long ID);
}
