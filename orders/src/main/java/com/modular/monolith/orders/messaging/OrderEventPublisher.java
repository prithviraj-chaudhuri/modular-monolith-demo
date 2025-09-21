package com.modular.monolith.orders.messaging;

import com.modular.monolith.common.events.OrderRequestedEvent;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final JmsTemplate jmsTemplate;

    public OrderEventPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishOrderRequested(OrderRequestedEvent event) {
        jmsTemplate.convertAndSend("order.requested.queue", event);
    }
}
