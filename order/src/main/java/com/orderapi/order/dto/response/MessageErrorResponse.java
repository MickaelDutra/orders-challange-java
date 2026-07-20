package com.orderapi.order.dto.response;

import org.springframework.http.HttpStatus;

public record MessageErrorResponse(HttpStatus status, String message) {
}
