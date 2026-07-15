package com.orderapi.order.controller.dto.response;

import com.orderapi.order.entity.Status;

import java.util.UUID;

public record UpdateStatusResponse(UUID id, int UserId, Status status) {
}
