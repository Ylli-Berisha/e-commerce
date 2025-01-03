package org.bisha.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.models.Product;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistDto {
    private UserDto user;

    private List<Product> products;
}
