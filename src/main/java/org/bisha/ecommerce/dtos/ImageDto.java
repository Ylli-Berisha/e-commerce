package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    @NotBlank(message = "Url cannot be empty")
    @Size(max = 255, message = "Url must be less than or equal to 255 characters")
    private String url;

    @NotNull(message = "Product cannot be null")
    private Product product;
}