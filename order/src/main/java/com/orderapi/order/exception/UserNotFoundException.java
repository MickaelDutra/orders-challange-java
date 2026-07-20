package com.orderapi.order.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super("Usuário não encontrado para o Id: " + userId);
    }
}
