package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.WishlistDto;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Wishlist;

import java.util.List;

public interface WishlistService {
    WishlistDto createWishlist(Long userId);

    WishlistDto getWishlistByUserId(Long userId);

    ProductDto addProductToWishlist(Long wishlistId, Long productId);

    ProductDto removeProductFromWishlist(Long wishlistId, Long productId);

    WishlistDto clearWishlist(Long wishlistId);

    List<ProductDto> getAllProductsInWishlist(Long wishlistId);

    boolean isProductInWishlist(Long wishlistId, Long productId);

    WishlistDto duplicateWishlist(Long wishlistId, Long userId);
}
