package com.orderapi.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int idItem;
    private BigDecimal price;
    private int amount;
    private BigDecimal partialAmount;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Order order;
}
