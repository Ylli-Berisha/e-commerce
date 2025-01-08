package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.ShoppingCartItemDto;

import java.util.List;

public interface ShoppingCartItemService {
    List<ShoppingCartItemDto> getItemsByShoppingCartId(Long shoppingCartId);

    ShoppingCartItemDto getItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    List<ShoppingCartItemDto> removeAllItemsByShoppingCartId(Long shoppingCartId);

    ShoppingCartItemDto removeItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    List<ShoppingCartItemDto> getItemsByShoppingCartIdAndQuantityGreaterThan(Long shoppingCartId, int quantity);

    ShoppingCartItemDto addItemToShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto);

    ShoppingCartItemDto updateItemInShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto);

    boolean existsByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);
}