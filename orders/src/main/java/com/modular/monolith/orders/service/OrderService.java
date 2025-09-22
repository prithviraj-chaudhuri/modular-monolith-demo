package com.modular.monolith.orders.service;

import com.modular.monolith.orders.entity.Order;
import com.modular.monolith.orders.repository.OrderRepository;
import com.modular.monolith.orders.messaging.OrderEventPublisher;
import com.modular.monolith.common.events.OrderRequestedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Transactional
    public Order createOrder(Long itemId, int quantity) {
        Order order = new Order(itemId, quantity, "PENDING");
        order = orderRepository.save(order);
        orderEventPublisher.publishOrderRequested(new OrderRequestedEvent(order.getId(), itemId, quantity));
        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
