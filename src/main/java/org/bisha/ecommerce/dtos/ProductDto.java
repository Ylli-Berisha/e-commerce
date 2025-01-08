package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Subcategory;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private double price;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock must be zero or positive")
    private Integer stock;

    @NotNull(message = "Category is mandatory")
    private Category category;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 100, message = "Brand must be less than or equal to 100 characters")
    private String brand;

    @NotNull(message = "Rating is mandatory")
    @DecimalMin(value = "0.0", message = "Rating must be zero or positive")
    @DecimalMax(value = "5.0", message = "Rating must be less than or equal to 5")
    private Double rating;

    @NotNull(message = "Subcategory is mandatory")
    private Subcategory subcategory;

    @NotNull(message = "Availability status is mandatory")
    private Boolean available;

    @NotNull(message = "Creation date is mandatory")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDate createdAt;
}