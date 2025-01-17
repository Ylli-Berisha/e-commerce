package org.bisha.ecommerce.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Product;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistDto {

    @NotNull(message = "User cannot be null")
    private long userId;

    private List<Long> productIds;
}