package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();

    }

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand command) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(command.getRestaurantId()))
                .products(command.getItems().stream().map(
                        orderItem ->
                                new Product(new ProductId(orderItem.getProductId()))
                        )
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand command) {
        return Order.builder()
                .customerId(new CustomerId(command.getCustomerId()))
                .restaurantId(new RestaurantId(command.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(command.getAddress()))
                .price(new Money(command.getPrice()))
                .items(orderItemsToOrderItemEntities(command.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> items) {
        return items.stream().map(item ->
                OrderItem.builder()
                        .product(new Product(new ProductId(item.getProductId())))
                        .price(new Money(item.getPrice()))
                        .quantity(item.getQuantity())
                        .subTotal(new Money(item.getSubTotal()))
                        .build()
        ).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }
}
