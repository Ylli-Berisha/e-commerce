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
    @NotNull(message = "Product ID cannot be null")
    private @Valid Long productId;

    @Positive(message = "Price must be positive")
    private double price;
}