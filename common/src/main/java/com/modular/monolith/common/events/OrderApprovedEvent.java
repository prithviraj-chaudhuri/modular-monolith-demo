package com.modular.monolith.common.events;

public class OrderApprovedEvent {
    private Long orderId;
    private Long itemId;
    private int quantity;

    public OrderApprovedEvent() {}

    public OrderApprovedEvent(Long orderId, Long itemId, int quantity) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getOrderId() { return orderId; }
    public Long getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
}
