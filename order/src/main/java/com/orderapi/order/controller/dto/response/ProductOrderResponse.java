package com.orderapi.order.controller.dto.response;

import java.math.BigDecimal;

public record ProductOrderResponse(int idItem, BigDecimal price, int amount, BigDecimal partialAmount) {
}
