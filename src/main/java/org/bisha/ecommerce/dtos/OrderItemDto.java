package org.bisha.ecommerce.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
    @NotNull(message = "Order item ID cannot be null")
    @PositiveOrZero(message = "Order item ID must be positive")
    private Long id;

    @NotNull(message = "Order item user cannot be null")
    private @Valid Long userId;

    @NotNull(message = "Product ID cannot be null")
    private @Valid Long productId;

    @NotNull(message = "Order ID cannot be null")
    private @Valid Long orderId;

    @Positive(message = "Price must be positive")
    private double price;
}
