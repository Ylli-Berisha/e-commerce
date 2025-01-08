package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Items cannot be null")
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items;

    @PositiveOrZero(message = "Total price must be zero or positive")
    private double totalPrice;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDateTime createdAt;

    @NotNull(message = "Update date cannot be null")
    @PastOrPresent(message = "Update date must be in the past or present")
    private LocalDateTime updatedAt;
}