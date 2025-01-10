package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartItemDto {
    @NotNull(message = "Product ID cannot be null")
    private long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Positive(message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private double price;
}