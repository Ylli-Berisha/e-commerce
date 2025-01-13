package org.bisha.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "wishlists")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "User cannot be null")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty(message = "Products list cannot be empty")
    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    @Column(name = "created_at")
    private LocalDate createdAt;
}