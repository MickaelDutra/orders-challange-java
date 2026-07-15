package com.orderapi.order.mapper;


import com.orderapi.order.controller.dto.response.OrderResponse;
import com.orderapi.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

   public OrderResponse toResponse (Order entity){
       return new OrderResponse(entity.getId(),entity.getUserId(), entity.getStatus(), entity.getTotalPrice(), entity.getProduct().stream().map(productMapper::toResponse).toList());
   }
}
