package com.greenvn.starlightelectronicsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
	
	Page<OrderDetail> findAll(Pageable pageable);
}
