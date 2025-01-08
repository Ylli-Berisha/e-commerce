package org.bisha.ecommerce.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Product;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotNull(message = "Product cannot be null")
    private Product product;

    @NotNull(message = "User cannot be null")
    private @Valid UserDto user;

    @NotNull(message = "Creation date is mandatory")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDateTime createdAt;
}