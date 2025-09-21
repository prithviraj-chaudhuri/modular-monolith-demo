package com.modular.monolith.common.events;

public class OrderRejectedEvent {
    private Long orderId;
    private Long itemId;
    private int quantity;
    private String reason;

    public OrderRejectedEvent() {}

    public OrderRejectedEvent(Long orderId, Long itemId, int quantity, String reason) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.reason = reason;
    }

    public Long getOrderId() { return orderId; }
    public Long getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
    public String getReason() { return reason; }
}
