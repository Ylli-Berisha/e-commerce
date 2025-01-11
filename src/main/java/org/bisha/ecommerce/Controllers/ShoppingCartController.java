package org.bisha.ecommerce.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.ShoppingCartDto;
import org.bisha.ecommerce.dtos.ShoppingCartItemDto;
import org.bisha.ecommerce.services.ShoppingCartItemService;
import org.bisha.ecommerce.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
@Validated
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @PostMapping("/{userId}/add-product")
    public ShoppingCartDto addProductToCart(@PathVariable @NotNull @Min(0) Long userId, @RequestBody @Valid ProductDto productDto) {
        return shoppingCartService.addProductToCart(userId, productDto);
    }

    @DeleteMapping("/{userId}/remove-product/{productId}")
    public ShoppingCartDto removeProductFromCart(@PathVariable @NotNull @Min(0) Long userId, @PathVariable @NotNull @Min(0) Long productId) {
        return shoppingCartService.removeProductFromCart(userId, productId);
    }

    @GetMapping("/{userId}")
    public ShoppingCartDto getCartByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public ShoppingCartDto clearCart(@PathVariable @NotNull @Min(0) Long userId) {
        return shoppingCartService.clearCart(userId);
    }

    @GetMapping("/{userId}/total-price")
    public double getTotalPrice(@PathVariable @NotNull @Min(0) Long userId) {
        return shoppingCartService.getTotalPrice(userId);
    }

    @GetMapping("/{userId}/product-count")
    public int getProductCount(@PathVariable @NotNull @Min(0) Long userId) {
        return shoppingCartService.getProductCount(userId);
    }

    @GetMapping("/{userId}/is-product-in-cart/{productId}")
    public boolean isProductInCart(@PathVariable @NotNull @Min(0) Long userId, @PathVariable @NotNull @Min(0) Long productId) {
        return shoppingCartService.isProductInCart(userId, productId);
    }

    @GetMapping("/{shoppingCartId}/items")
    public List<ShoppingCartItemDto> getItemsByShoppingCartId(@PathVariable @NotNull @Min(0) Long shoppingCartId) {
        return shoppingCartItemService.getItemsByShoppingCartId(shoppingCartId);
    }

    @GetMapping("/{shoppingCartId}/item/{productId}")
    public ShoppingCartItemDto getItemByShoppingCartIdAndProductId(@PathVariable @NotNull @Min(0) Long shoppingCartId, @PathVariable @NotNull @Min(0) Long productId) {
        return shoppingCartItemService.getItemByShoppingCartIdAndProductId(shoppingCartId, productId);
    }

    @DeleteMapping("/{shoppingCartId}/items/clear")
    public List<ShoppingCartItemDto> removeAllItemsByShoppingCartId(@PathVariable @NotNull @Min(0) Long shoppingCartId) {
        return shoppingCartItemService.removeAllItemsByShoppingCartId(shoppingCartId);
    }

    @DeleteMapping("/{shoppingCartId}/item/{productId}")
    public ShoppingCartItemDto removeItemByShoppingCartIdAndProductId(@PathVariable @NotNull @Min(0) Long shoppingCartId, @PathVariable @NotNull @Min(0) Long productId) {
        return shoppingCartItemService.removeItemByShoppingCartIdAndProductId(shoppingCartId, productId);
    }

    @GetMapping("/{shoppingCartId}/items/quantity-greater-than/{quantity}")
    public List<ShoppingCartItemDto> getItemsByShoppingCartIdAndQuantityGreaterThan(@PathVariable @NotNull @Min(0) Long shoppingCartId, @PathVariable @NotNull @Min(0) int quantity) {
        return shoppingCartItemService.getItemsByShoppingCartIdAndQuantityGreaterThan(shoppingCartId, quantity);
    }

    @PostMapping("/{shoppingCartId}/add-item")
    public ShoppingCartItemDto addItemToShoppingCart(@PathVariable @NotNull @Min(0) Long shoppingCartId, @RequestBody @Valid ShoppingCartItemDto shoppingCartItemDto) {
        return shoppingCartItemService.addItemToShoppingCart(shoppingCartId, shoppingCartItemDto);
    }

    @PutMapping("/{shoppingCartId}/update-item")
    public ShoppingCartItemDto updateItemInShoppingCart(@PathVariable @NotNull @Min(0) Long shoppingCartId, @RequestBody @Valid ShoppingCartItemDto shoppingCartItemDto) {
        return shoppingCartItemService.updateItemInShoppingCart(shoppingCartId, shoppingCartItemDto);
    }

    @GetMapping("/get-dto")
    public ShoppingCartDto getDto() {
        return new ShoppingCartDto();
    }
}