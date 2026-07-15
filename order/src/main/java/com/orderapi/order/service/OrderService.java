package com.orderapi.order.service;

import com.orderapi.order.client.ProductClient;
import com.orderapi.order.client.UserClient;
import com.orderapi.order.controller.dto.request.OrderRequest;
import com.orderapi.order.controller.dto.request.ProductRequest;
import com.orderapi.order.controller.dto.response.OrderResponse;
import com.orderapi.order.controller.dto.response.ProductResponse;
import com.orderapi.order.entity.Order;
import com.orderapi.order.entity.Product;
import com.orderapi.order.entity.Status;
import com.orderapi.order.mapper.OrderMapper;
import com.orderapi.order.mapper.ProductMapper;
import com.orderapi.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, UserClient userClient,
                        ProductClient productClient, ProductMapper productMapper, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
    }

    public OrderResponse addOrder(OrderRequest request) {
        if(userClient.getUser(request.userId()) == null){
            throw new RuntimeException("Usuário não encontrado");
        }

        Map<Integer, Product> productsMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ProductRequest productRequest : request.products()){
            ProductResponse productResponse = productClient.getProduct(productRequest.id());

            Product product = productsMap.get(productResponse.id());

            if(product == null){
                product = productMapper.toEntity(productResponse);
                productsMap.put(productResponse.id(), product);
            }else{
                product.setAmount(1 + product.getAmount());
                product.setPartialAmount(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));
            }
            total = productsMap.values().stream().map(Product::getPartialAmount).reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
        }


        Order order = new Order();

        order.setUserId(request.userId());
        order.setStatus(Status.PENDING);
        order.setTotalPrice(total);
        order.setProduct(new ArrayList<>(productsMap.values()));

        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }
}
