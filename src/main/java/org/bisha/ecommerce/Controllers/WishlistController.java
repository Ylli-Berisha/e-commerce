package org.bisha.ecommerce.Controllers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.WishlistDto;
import org.bisha.ecommerce.services.WishlistService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
@Validated
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/create/{userId}")
    public WishlistDto createWishlist(@PathVariable @NotNull @Min(1) Long userId) {
        return wishlistService.createWishlist(userId);
    }

    @GetMapping("/user/{userId}")
    public WishlistDto getWishlistByUserId(@PathVariable @NotNull @Min(1) Long userId) {
        return wishlistService.getWishlistByUserId(userId);
    }

    @PostMapping("/{wishlistId}/add/{productId}")
    public ProductDto addProductToWishlist(@PathVariable @NotNull @Min(1) Long wishlistId,
                                           @PathVariable @NotNull @Min(1) Long productId) {
        return wishlistService.addProductToWishlist(wishlistId, productId);
    }

    @DeleteMapping("/{wishlistId}/remove/{productId}")
    public ProductDto removeProductFromWishlist(@PathVariable @NotNull @Min(1) Long wishlistId,
                                                @PathVariable @NotNull @Min(1) Long productId) {
        return wishlistService.removeProductFromWishlist(wishlistId, productId);
    }

    @DeleteMapping("/{wishlistId}/clear")
    public WishlistDto clearWishlist(@PathVariable @NotNull @Min(1) Long wishlistId) {
        return wishlistService.clearWishlist(wishlistId);
    }

    @GetMapping("/{wishlistId}/products")
    public List<ProductDto> getAllProductsInWishlist(@PathVariable @NotNull @Min(1) Long wishlistId) {
        return wishlistService.getAllProductsInWishlist(wishlistId);
    }

    @GetMapping("/{wishlistId}/contains/{productId}")
    public boolean isProductInWishlist(@PathVariable @NotNull @Min(1) Long wishlistId,
                                       @PathVariable @NotNull @Min(1) Long productId) {
        return wishlistService.isProductInWishlist(wishlistId, productId);
    }

    @PostMapping("/{wishlistId}/duplicate/{userId}")
    public WishlistDto duplicateWishlist(@PathVariable @NotNull @Min(1) Long wishlistId,
                                         @PathVariable @NotNull @Min(1) Long userId) {
        return wishlistService.duplicateWishlist(wishlistId, userId);
    }
}