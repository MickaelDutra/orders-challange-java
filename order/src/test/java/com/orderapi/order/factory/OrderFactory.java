package com.orderapi.order.factory;

import com.orderapi.order.entity.Order;
import com.orderapi.order.entity.Product;
import com.orderapi.order.entity.Status;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles("test")
@Configuration
public class OrderFactory {


    public static Order createAndPersistOrder(int userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(Status.PENDING);
        order.setTotalPrice(BigDecimal.valueOf(100));

        Product product = new Product();
        product.setIdItem(1);
        product.setPrice(BigDecimal.valueOf(50));
        product.setAmount(2);
        product.setPartialAmount(BigDecimal.valueOf(100));
        order.addProduct(product);

        return order;
    }
}
