package com.orderapi.order.controller.dto.response;

import java.math.BigDecimal;

public record ProductResponse(int id, BigDecimal price) {
}
