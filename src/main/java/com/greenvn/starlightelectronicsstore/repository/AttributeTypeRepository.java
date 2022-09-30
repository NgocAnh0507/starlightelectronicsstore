package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType,Long>{

	Page< AttributeType > findAll(Pageable pageable);

	@Query("SELECT a FROM AttributeType AS a WHERE a.name = :aname")
	AttributeType findAttributeTypeByName(@Param("aname") String name);
}
