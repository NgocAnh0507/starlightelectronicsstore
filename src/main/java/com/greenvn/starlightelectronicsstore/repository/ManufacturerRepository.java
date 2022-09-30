package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.entities.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long>{

	Page<Manufacturer> findAll(Pageable pageable);

	@Query("SELECT m FROM Manufacturer AS m WHERE m.name = :mname")
	Manufacturer findManufacturerByName(@Param("mname") String name);
}
