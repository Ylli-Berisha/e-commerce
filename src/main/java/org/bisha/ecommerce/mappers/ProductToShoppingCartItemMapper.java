package org.bisha.ecommerce.mappers;

import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.ShoppingCartItem;
import org.mapstruct.Mapper;

import java.util.List;

public interface ProductToShoppingCartItemMapper {
    Product mapToProduct(ShoppingCartItem shoppingCartItem);
    ShoppingCartItem mapToShoppingCartItem(Product product);

    List<ShoppingCartItem> mapToShoppingCartItems(List<Product> products);
    List<Product> mapToProducts(List<ShoppingCartItem> shoppingCartItems);
}
