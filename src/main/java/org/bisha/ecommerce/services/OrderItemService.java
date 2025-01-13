package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);

    OrderItemDto getOrderItemById(Long id);

    List<OrderItemDto> getAllOrderItems();

    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);

    OrderItemDto deleteOrderItem(Long id);
}