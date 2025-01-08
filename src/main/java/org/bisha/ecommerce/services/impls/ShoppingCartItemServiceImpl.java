package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.ShoppingCartItemDto;
import org.bisha.ecommerce.mappers.ShoppingCartItemMapper;
import org.bisha.ecommerce.models.ShoppingCart;
import org.bisha.ecommerce.models.ShoppingCartItem;
import org.bisha.ecommerce.repositories.ShoppingCartRepository;
import org.bisha.ecommerce.services.ShoppingCartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    public ShoppingCartItemServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemMapper shoppingCartItemMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
    }

    @Override
    public List<ShoppingCartItemDto> getItemsByShoppingCartId(Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCartItemDto getItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem item = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
        return shoppingCartItemMapper.toDto(item);
    }

    @Override
    public List<ShoppingCartItemDto> removeAllItemsByShoppingCartId(Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        List<ShoppingCartItemDto> items = shoppingCart.getItems().stream()
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);
        return items;
    }

    @Override
    public ShoppingCartItemDto removeItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem item = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
        shoppingCart.getItems().remove(item);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(item);
    }

    @Override
    public List<ShoppingCartItemDto> getItemsByShoppingCartIdAndQuantityGreaterThan(Long shoppingCartId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .filter(item -> item.getQuantity() > quantity)
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCartItemDto addItemToShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto) {
        validateShoppingCartItemDto(shoppingCartItemDto);
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        if (shoppingCart.getItems().stream().anyMatch(item -> item.getProduct().getId() == shoppingCartItemDto.getProductId())) {
            throw new IllegalArgumentException("Product already exists in cart");
        }
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.toEntity(shoppingCartItemDto);
        shoppingCart.getItems().add(shoppingCartItem);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(shoppingCartItem);
    }

    @Override
    public ShoppingCartItemDto updateItemInShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto) {
        validateShoppingCartItemDto(shoppingCartItemDto);
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem shoppingCartItem = shoppingCart.getItems().stream()
                .filter(item -> item.getProduct().getId() == shoppingCartItemDto.getProductId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
        shoppingCartItem.setQuantity(shoppingCartItemDto.getQuantity());
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(shoppingCartItem);
    }

    @Override
    public boolean existsByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId() == productId);
    }

    private void validateShoppingCartItemDto(ShoppingCartItemDto shoppingCartItemDto) {
        if (shoppingCartItemDto == null || shoppingCartItemDto.getProductId() == null || shoppingCartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid shopping cart item");
        }
    }
}