package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.ShoppingCartItem;

import java.util.List;

public interface ProductDtoToShoppingCartItemMapper {
    ProductDto ToProduct(ShoppingCartItem shoppingCartItem);
    ShoppingCartItem mapToShoppingCartItem(ProductDto product);

    List<ShoppingCartItem> mapToShoppingCartItems(List<ProductDto> products);
    List<ProductDto> mapToProducts(List<ShoppingCartItem> shoppingCartItems);
}
