package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long>{
	
	Page<Position> findAll(Pageable pageable);
	
	@Query("SELECT p FROM Position AS p WHERE p.name = :pname")
	Position findPositionByName(@Param("pname") String name);
}
