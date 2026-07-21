package com.orderapi.order.repository;

import com.orderapi.order.entity.Order;
import com.orderapi.order.entity.Product;
import com.orderapi.order.entity.Status;
import com.orderapi.order.factory.OrderFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    private OrderFactory orderFactory;

    @Test
    @DisplayName("Should find an order by userId and status")
    void findByUserIdAndStatusShouldReturnOrderWhenExists() {
        Order savedOrder = OrderFactory.createAndPersistOrder(10);
        entityManager.persist(savedOrder);
        entityManager.find(Order.class, savedOrder.getId());

        Optional<Order> result = orderRepository.findByUserIdAndStatus(10, Status.PENDING);

        assertTrue(result.isPresent());
        assertEquals(savedOrder.getId(), result.get().getId());
        assertEquals(10, result.get().getUserId());
        assertEquals(Status.PENDING, result.get().getStatus());
    }

    @Test
    @DisplayName("Should return empty when no order matches the userId and status")
    void findByUserIdAndStatusShouldReturnEmptyWhenNoMatch() {
        Order order = OrderFactory.createAndPersistOrder(10);
        order.setStatus(Status.CONCLUDED);

        Optional<Order> result = orderRepository.findByUserIdAndStatus(10, Status.PENDING);

        assertTrue(result.isEmpty());
    }
}