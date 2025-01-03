package org.bisha.ecommerce.dtos;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    @NotNull
    @NotBlank(message = "Url cannot be empty")
    private String url;

    @NotNull(message = "Product cannot be null")
    private Product product;
}
