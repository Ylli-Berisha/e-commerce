package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.WishlistDto;

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

    WishlistDto getWishlistById(Long wishlistId);
}
