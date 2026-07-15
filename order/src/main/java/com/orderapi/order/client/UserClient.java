package com.orderapi.order.client;

import com.orderapi.order.controller.dto.response.UserResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponse getUser(int id){
        return restClient.get()
                .uri("https://fakestoreapi.com/users/{id}", id)
                .retrieve()
                .body(UserResponse.class);
    }
}
