package org.bisha.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDto {
    private UserDto user;
    private List<ShoppingCartItemDto> items;
    private double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}