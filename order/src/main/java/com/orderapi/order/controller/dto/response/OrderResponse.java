package com.orderapi.order.controller.dto.response;

import com.orderapi.order.entity.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, int userId, Status status, BigDecimal totalPrice, List<ProductOrderResponse> product) {
}
