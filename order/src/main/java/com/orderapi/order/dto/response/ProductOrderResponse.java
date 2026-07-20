package com.orderapi.order.dto.response;

import java.math.BigDecimal;

public record ProductOrderResponse(int idItem, BigDecimal price, int amount, BigDecimal partialAmount) {
}
