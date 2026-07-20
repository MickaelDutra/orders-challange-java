package com.orderapi.order.controller;

import com.orderapi.order.controller.dto.request.OrderRequest;
import com.orderapi.order.controller.dto.response.OrderResponse;
import com.orderapi.order.controller.dto.response.UpdateStatusResponse;
import com.orderapi.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/pedido")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public OrderResponse addOrder(@Validated @RequestBody OrderRequest request){
        return orderService.addOrder(request);
    }

    @PutMapping("atualizar-status-pedido/{id}")
    public UpdateStatusResponse updateStatus(@PathVariable UUID id){
        return orderService.updateStatus(id);
    }

    @PutMapping("atualizar-item-pedido")
    public OrderResponse updateItemOrder(@Validated @RequestBody OrderRequest request){
        return orderService.udateItemOrder(request);

    }

}
