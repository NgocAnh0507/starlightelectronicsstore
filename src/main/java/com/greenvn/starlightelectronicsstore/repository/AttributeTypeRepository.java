package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType,Integer>{

}
