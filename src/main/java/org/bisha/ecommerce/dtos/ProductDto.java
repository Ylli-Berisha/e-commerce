package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Subcategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private double price;

    @NotNull(message = "Stock is mandatory")
    private int stock;

    @NotNull(message = "Category is mandatory")
    private Category category;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotNull(message = "Rating is mandatory")
    private double rating;

    @NotNull(message = "Subcategory is mandatory")
    private Subcategory subcategory;

    @NotNull(message = "Availability status is mandatory")
    private boolean isAvailable;

    @NotNull(message = "Creation date is mandatory")
    private LocalDate createdAt;
}