package com.orderapi.order.config;

import com.orderapi.order.dto.response.MessageErrorResponse;
import com.orderapi.order.exception.OrderConcludedException;
import com.orderapi.order.exception.OrderNotFoundException;
import com.orderapi.order.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<MessageErrorResponse> userNotFound (UserNotFoundException userNotFoundException){
        MessageErrorResponse messageErrorResponse = new MessageErrorResponse(HttpStatus.NOT_FOUND, userNotFoundException.getMessage());
        return ResponseEntity.status(messageErrorResponse.status()).body(messageErrorResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<MessageErrorResponse> orderNotFound (OrderNotFoundException orderNotFoundException){
        MessageErrorResponse messageErrorResponse = new MessageErrorResponse(HttpStatus.NOT_FOUND, orderNotFoundException.getMessage());
        return ResponseEntity.status(messageErrorResponse.status()).body(messageErrorResponse);
    }

    @ExceptionHandler(OrderConcludedException.class)
    private ResponseEntity<MessageErrorResponse> orderConcluded (OrderConcludedException orderConcludedException){
        MessageErrorResponse messageErrorResponse = new MessageErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT, orderConcludedException.getMessage());
        return ResponseEntity.status(messageErrorResponse.status()).body(messageErrorResponse);
    }
}
