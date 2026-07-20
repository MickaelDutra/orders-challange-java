package com.orderapi.order.mapper;

import com.orderapi.order.dto.response.UpdateStatusResponse;
import com.orderapi.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusMapper {

    public UpdateStatusResponse toResponse (Order entity){
        return new UpdateStatusResponse(entity.getId(), entity.getUserId(), entity.getStatus());
    }
}
