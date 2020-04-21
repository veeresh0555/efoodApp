package com.fapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fapp.model.OrderDetails;
@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Long> {

}
