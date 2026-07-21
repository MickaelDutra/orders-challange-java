package com.orderapi.order.service;

import com.orderapi.order.client.ProductClient;
import com.orderapi.order.client.UserClient;
import com.orderapi.order.dto.request.OrderRequest;
import com.orderapi.order.dto.request.ProductRequest;
import com.orderapi.order.dto.response.OrderResponse;
import com.orderapi.order.dto.response.ProductResponse;
import com.orderapi.order.dto.response.UpdateStatusResponse;
import com.orderapi.order.dto.response.UserResponse;
import com.orderapi.order.entity.Order;
import com.orderapi.order.entity.Status;
import com.orderapi.order.exception.OrderConcludedException;
import com.orderapi.order.exception.UserNotFoundException;
import com.orderapi.order.factory.OrderFactory;
import com.orderapi.order.mapper.OrderMapper;
import com.orderapi.order.mapper.ProductMapper;
import com.orderapi.order.mapper.UpdateStatusMapper;
import com.orderapi.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private ProductClient productClient;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ProductMapper productMapper = new ProductMapper();
        OrderMapper orderMapper = new OrderMapper(productMapper);
        UpdateStatusMapper updateStatusMapper = new UpdateStatusMapper();
        orderService = new OrderService(orderRepository, userClient, productClient, productMapper, orderMapper, updateStatusMapper);
    }

    @Test
    @DisplayName("Should create a new order when the user exists and no pending order is found")
    void addOrderShouldCreateNewOrderWhenNoPendingOrderExists() {
        OrderRequest request = new OrderRequest(1, List.of(new ProductRequest(10)));

        when(userClient.getUser(1)).thenReturn(new UserResponse(1));
        when(productClient.getProduct(10)).thenReturn(new ProductResponse(10, BigDecimal.valueOf(50)));
        when(orderRepository.findByUserIdAndStatus(1, Status.PENDING)).thenReturn(Optional.empty());

        OrderResponse response = orderService.addOrder(request);

        assertNotNull(response);
        assertEquals(1, response.userId());
        assertEquals(Status.PENDING, response.status());
        assertEquals(BigDecimal.valueOf(50), response.totalPrice());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should exception USerNotFound when user id is not found")
    void addOrderShouldThrowWhenUserDoesNotFound() {
        OrderRequest request = new OrderRequest(1, List.of(new ProductRequest(10)));

        when(userClient.getUser(99)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> orderService.addOrder(request));
        verify(orderRepository, never()).save(any(Order.class));

    }

    @Test
    @DisplayName("Should update existent order if its pending to user id")
    void shouldUpdateAnOrder() {
        Order existentOrder = OrderFactory.createAndPersistOrder(1);
        OrderRequest request = new OrderRequest(1, List.of(new ProductRequest(10)));

        when(userClient.getUser(1)).thenReturn(new UserResponse(1));
        when(orderRepository.findByUserIdAndStatus(1, Status.PENDING)).thenReturn(Optional.of(existentOrder));
        when(productClient.getProduct(10)).thenReturn(new ProductResponse(10, BigDecimal.valueOf(50)));

        OrderResponse response = orderService.addOrder(request);

        assertNotNull(response);
        assertEquals(1, response.userId());
        assertEquals(Status.PENDING, response.status());
        verify(orderRepository).save(existentOrder);
    }

    @Test
    @DisplayName("Should change status order to concluded if its pending")
    void ShouldChangeStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = OrderFactory.createAndPersistOrder(1);
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        UpdateStatusResponse statusResponse = orderService.updateStatus(orderId);

        assertNotNull(statusResponse);
        assertEquals(Status.CONCLUDED, statusResponse.status());
        verify(orderRepository).save(order);

    }

    @Test
    @DisplayName("Should throw exception if status is concluded")
    void shouldNotChangeStatusWhenStatusIsConcluded() {
        UUID orderId = UUID.randomUUID();
        Order order = OrderFactory.createAndPersistOrder(1);
        order.setId(orderId);
        order.setStatus(Status.CONCLUDED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(OrderConcludedException.class, () -> orderService.updateStatus(orderId));
        verify(orderRepository, never()).save(any(Order.class));
    }
}