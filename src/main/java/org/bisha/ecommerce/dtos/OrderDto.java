package org.bisha.ecommerce.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "User cannot be null")
    private UserDto user;

    @NotNull(message = "Order date cannot be null")
    @PastOrPresent(message = "Order date must be in the past or present")
    private LocalDateTime orderDate;

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @PositiveOrZero(message = "Total amount must be zero or positive")
    @Digits(integer = 10, fraction = 2, message = "Total amount must be a valid monetary amount")
    private double totalAmount;

    @NotEmpty(message = "Order items list cannot be empty")
    private List<@Valid OrderItemDto> orderItems;
}