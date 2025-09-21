package com.modular.monolith.orders.repository;

import com.modular.monolith.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
