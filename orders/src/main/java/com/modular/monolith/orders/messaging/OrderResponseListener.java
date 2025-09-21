package com.modular.monolith.orders.messaging;

import com.modular.monolith.common.events.OrderApprovedEvent;
import com.modular.monolith.common.events.OrderRejectedEvent;
import com.modular.monolith.orders.repository.OrderRepository;
import com.modular.monolith.orders.entity.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderResponseListener {

    private final OrderRepository orderRepository;

    public OrderResponseListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @JmsListener(destination = "order.approved.queue")
    public void handleOrderApproved(OrderApprovedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
        order.setStatus("APPROVED");
        orderRepository.save(order);
    }

    @Transactional
    @JmsListener(destination = "order.rejected.queue")
    public void handleOrderRejected(OrderRejectedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
        order.setStatus("REJECTED");
        orderRepository.save(order);
    }
}
