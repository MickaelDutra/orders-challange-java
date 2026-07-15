package com.orderapi.order.controller;

import com.orderapi.order.controller.dto.request.OrderRequest;
import com.orderapi.order.controller.dto.response.OrderResponse;
import com.orderapi.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public OrderResponse addOrder(@Validated @RequestBody OrderRequest request){
        return orderService.addOrder(request);
    }

}
