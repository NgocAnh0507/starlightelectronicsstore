package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long>{
	@Query("SELECT e FROM Employee AS e WHERE userName = :uname")
	Employee findEmployeeByUserName(@Param("uname") String userName);
}
