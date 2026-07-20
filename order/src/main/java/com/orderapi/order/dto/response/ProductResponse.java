package com.orderapi.order.dto.response;

import java.math.BigDecimal;

public record ProductResponse(int id, BigDecimal price) {
}
