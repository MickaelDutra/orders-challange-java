package com.orderapi.order.exception;

public class OrderConcludedException extends RuntimeException {
    public OrderConcludedException() {
        super("Pedido já concluido");
    }
}