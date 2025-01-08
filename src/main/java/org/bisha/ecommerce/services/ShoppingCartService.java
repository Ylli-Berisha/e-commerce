package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.ShoppingCartDto;
import org.bisha.ecommerce.models.Product;

public interface ShoppingCartService {

    ShoppingCartDto addProductToCart(Long userId, Product product);

    ShoppingCartDto removeProductFromCart(Long userId, Long productId);

    ShoppingCartDto getCartByUserId(Long userId);

    ShoppingCartDto clearCart(Long userId);

    double getTotalPrice(Long userId);

    int getProductCount(Long userId);

    boolean isProductInCart(Long userId, Long productId);

}