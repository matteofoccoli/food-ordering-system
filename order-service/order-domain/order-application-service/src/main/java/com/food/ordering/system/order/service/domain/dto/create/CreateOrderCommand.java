package com.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
    @NotNull
    private final UUID orderId;
    @NotNull
    private final UUID restaurantId;

    @NotNull
    private final UUID customerId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress address;

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderAddress getAddress() {
        return address;
    }
}
