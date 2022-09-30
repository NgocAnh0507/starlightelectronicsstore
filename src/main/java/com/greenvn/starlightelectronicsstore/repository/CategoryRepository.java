package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

	Page<Category> findAll(Pageable pageable);

	@Query("SELECT c FROM Category AS c WHERE c.name = :cname")
	Category findCategoryByName(@Param("cname") String name);
}
