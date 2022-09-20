package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer>{

}
