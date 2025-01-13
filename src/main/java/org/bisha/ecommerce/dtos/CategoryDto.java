package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Subcategory;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    private String description;

    private List<@PositiveOrZero Long> subcategoryIds;
}