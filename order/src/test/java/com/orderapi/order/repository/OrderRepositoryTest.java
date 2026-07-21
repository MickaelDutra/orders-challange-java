package com.orderapi.order.repository;

import com.orderapi.order.entity.Order;
import com.orderapi.order.entity.Product;
import com.orderapi.order.entity.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should find an order by userId and status")
    void findByUserIdAndStatusShouldReturnOrderWhenExists() {
        Order savedOrder = createAndPersistOrder(10, Status.PENDING);

        Optional<Order> result = orderRepository.findByUserIdAndStatus(10, Status.PENDING);

        assertTrue(result.isPresent());
        assertEquals(savedOrder.getId(), result.get().getId());
        assertEquals(10, result.get().getUserId());
        assertEquals(Status.PENDING, result.get().getStatus());
    }

    @Test
    @DisplayName("Should return empty when no order matches the userId and status")
    void findByUserIdAndStatusShouldReturnEmptyWhenNoMatch() {
        createAndPersistOrder(10, Status.CONCLUDED);

        Optional<Order> result = orderRepository.findByUserIdAndStatus(10, Status.PENDING);

        assertTrue(result.isEmpty());
    }

    private Order createAndPersistOrder(int userId, Status status) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(status);
        order.setTotalPrice(BigDecimal.valueOf(100));

        Product product = new Product();
        product.setIdItem(1);
        product.setPrice(BigDecimal.valueOf(50));
        product.setAmount(2);
        product.setPartialAmount(BigDecimal.valueOf(100));
        order.addProduct(product);

        entityManager.persist(order);

        return entityManager.find(Order.class, order.getId());
    }
}