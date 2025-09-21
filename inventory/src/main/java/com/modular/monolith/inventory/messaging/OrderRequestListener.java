package com.modular.monolith.inventory.messaging;

import com.modular.monolith.common.events.OrderRequestedEvent;
import com.modular.monolith.common.events.OrderApprovedEvent;
import com.modular.monolith.common.events.OrderRejectedEvent;
import com.modular.monolith.inventory.repository.ItemRepository;
import com.modular.monolith.inventory.entity.Item;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderRequestListener {

    private final ItemRepository itemRepository;
    private final JmsTemplate jmsTemplate;

    public OrderRequestListener(ItemRepository itemRepository, JmsTemplate jmsTemplate) {
        this.itemRepository = itemRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    @JmsListener(destination = "order.requested.queue")
    public void handleOrderRequested(OrderRequestedEvent event) {
        Item item = itemRepository.findById(event.getItemId()).orElse(null);

        if (item == null) {
            jmsTemplate.convertAndSend("order.rejected.queue",
                    new OrderRejectedEvent(event.getOrderId(), event.getItemId(), event.getQuantity(), "Item not found"));
            return;
        }

        if (item.getCount() >= event.getQuantity()) {
            item.setCount(item.getCount() - event.getQuantity());
            itemRepository.save(item);

            jmsTemplate.convertAndSend("order.approved.queue",
                    new OrderApprovedEvent(event.getOrderId(), event.getItemId(), event.getQuantity()));
        } else {
            jmsTemplate.convertAndSend("order.rejected.queue",
                    new OrderRejectedEvent(event.getOrderId(), event.getItemId(), event.getQuantity(), "Insufficient stock"));
        }
    }
}
