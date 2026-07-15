package com.orderapi.order.controller.dto.request;

import java.util.List;

public record OrderRequest(int userId, List<ProductRequest> products) {
}
