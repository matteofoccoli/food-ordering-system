package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper,
                                    OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery query) {
        Optional<Order> order = orderRepository.findByTrackingId(
                new TrackingId(query.getOrderTrackingId()));
        if (order.isEmpty()) {
            log.warn("Cannot find order with traking id {}", query.getOrderTrackingId());
            throw new OrderNotFoundException(
                    "Cannot find order with traking id " + query.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(order.get());
    }
}
