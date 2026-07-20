package com.orderapi.order.dto.request;

import java.util.List;

public record OrderRequest(int userId, List<ProductRequest> products) {
}
