package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.OrderDto;
import org.bisha.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getAllOrders();

    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    OrderDto cancelOrderById(Long orderId);

    List<OrderDto> getOrdersByUserId(Long userId);

    List<OrderDto> getOrdersByStatus(OrderStatus status);

    OrderDto updateOrderStatus(Long orderId, OrderStatus status);

    public List<OrderDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    public List<OrderDto> getOrdersByTotalPriceRange(double minPrice, double maxPrice);

    public OrderDto updateOrderAddressAndPhone(Long orderId, String address, String phone);

}