package com.orderapi.order.client;

import com.orderapi.order.controller.dto.response.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProduct(int id){
        return restClient.get()
                .uri("https://fakestoreapi.com/products/{id}", id)
                .retrieve()
                .body(ProductResponse.class);
    }
}
