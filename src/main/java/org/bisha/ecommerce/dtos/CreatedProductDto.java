package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.*;
import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Image;
import org.bisha.ecommerce.models.Subcategory;

import java.util.List;

public class CreatedProductDto {
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

    private Category category;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 100, message = "Brand must be less than or equal to 100 characters")
    private String brand;

    private Subcategory subcategory;

    @NotNull(message = "Availability status is mandatory")
    private Boolean available;

    @NotEmpty(message = "Image URLs list cannot be empty")
    @Size(min = 1, message = "There must be at least one image URL")
    private List<Image> imageUrls;
}
