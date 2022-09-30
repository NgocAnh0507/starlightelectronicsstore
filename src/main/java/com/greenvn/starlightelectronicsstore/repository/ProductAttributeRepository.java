package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long>{

	Page<ProductAttribute> findAll(Pageable pageable);
}
