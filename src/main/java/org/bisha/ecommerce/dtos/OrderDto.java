package org.bisha.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private UserDto user;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemDto> orderItems;
}