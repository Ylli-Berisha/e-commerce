package org.bisha.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartItemDto {
    private Long productId;
    private int quantity;
    private double price;
}