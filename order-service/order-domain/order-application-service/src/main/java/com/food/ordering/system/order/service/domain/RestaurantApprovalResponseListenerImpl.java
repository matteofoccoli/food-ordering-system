package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
public class RestaurantApprovalResponseListenerImpl implements
        RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse response) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse response) {

    }
}
