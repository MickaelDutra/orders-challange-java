package com.orderapi.order.mapper;


import com.orderapi.order.dto.response.ProductOrderResponse;
import com.orderapi.order.dto.response.ProductResponse;
import com.orderapi.order.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

   public Product toEntity (ProductResponse response){
       Product product = new Product();
       product.setIdItem(response.id());
       product.setPrice(response.price());
       product.setAmount(1);
       product.setPartialAmount(response.price());
       return product;
   }

   public ProductOrderResponse toResponse(Product entity){
       return new ProductOrderResponse(entity.getIdItem(), entity.getPrice(), entity.getAmount(), entity.getPartialAmount());
   }
}
