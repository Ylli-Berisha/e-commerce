package org.bisha.ecommerce.mappers.Impl;

import org.bisha.ecommerce.mappers.ProductToShoppingCartItemMapper;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.ShoppingCartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductToShoppingCartItemMapperImpl implements ProductToShoppingCartItemMapper {
    @Override
    public Product mapToProduct(ShoppingCartItem shoppingCartItem) {
        if (shoppingCartItem == null) {
            return null;
        }
         return shoppingCartItem.getProduct();

    }

    @Override
    public ShoppingCartItem mapToShoppingCartItem(Product product) {
        if (product == null) {
            return null;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(1); // Default quantity, adjust as necessary
        shoppingCartItem.setPrice(product.getPrice());
        return shoppingCartItem;
    }

    @Override
    public List<ShoppingCartItem> mapToShoppingCartItems(List<Product> products) {
        if (products == null) {
            return List.of();
        }
        return products.stream()
                .map(this::mapToShoppingCartItem)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> mapToProducts(List<ShoppingCartItem> shoppingCartItems) {
        if (shoppingCartItems == null) {
            return List.of();
        }
        return shoppingCartItems.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }
}