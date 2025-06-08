package com.droute.orderservice.enums;

public enum OrderStatus {
    PENDING,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    DELAYED,
    //Courier status
    SAVED,
    REQUESTED,
    REJECTED,
    PICKED_UP,
    DELIVERED;
    

    public static OrderStatus fromString(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }
}
