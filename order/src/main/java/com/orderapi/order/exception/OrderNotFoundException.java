package com.orderapi.order.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID orderId) {
        super("Pedido não encontrado para o Id: " + orderId);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
