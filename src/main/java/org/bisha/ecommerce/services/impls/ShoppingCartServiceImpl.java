package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.ShoppingCartDto;
import org.bisha.ecommerce.dtos.ShoppingCartItemDto;
import org.bisha.ecommerce.mappers.ProductToShoppingCartItemMapper;
import org.bisha.ecommerce.mappers.ShoppingCartItemMapper;
import org.bisha.ecommerce.mappers.ShoppingCartMapper;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.ShoppingCart;
import org.bisha.ecommerce.models.ShoppingCartItem;
import org.bisha.ecommerce.services.ShoppingCartItemService;
import org.bisha.ecommerce.services.ShoppingCartService;
import org.bisha.ecommerce.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ProductToShoppingCartItemMapper productToShoppingCartItemMapper;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper, ProductToShoppingCartItemMapper productToShoppingCartItemMapper, ShoppingCartItemService shoppingCartItemService, ShoppingCartItemMapper shoppingCartItemMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.productToShoppingCartItemMapper = productToShoppingCartItemMapper;
        this.shoppingCartItemService = shoppingCartItemService;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
    }

    @Override
    public ShoppingCartDto addProductToCart(Long userId, Product product) {
        validateUserId(userId);
        validateProduct(product);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        if (shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), product.getId())) {
            throw new IllegalArgumentException("Product already exists in cart");
        }

        ShoppingCartItem shoppingCartItem = productToShoppingCartItemMapper.mapToShoppingCartItem(product);
        ShoppingCartItemDto shoppingCartItemDto = shoppingCartItemMapper.toDto(shoppingCartItem);
        shoppingCartItemService.addItemToShoppingCart(shoppingCart.getId(), shoppingCartItemDto);

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + product.getPrice());
        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto removeProductFromCart(Long userId, Long productId) {
        validateUserId(userId);
        validateProductId(productId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        ShoppingCartItemDto removedItem = shoppingCartItemService.removeItemByShoppingCartIdAndProductId(shoppingCart.getId(), productId);

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - removedItem.getPrice());
        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getCartByUserId(Long userId) {
        validateUserId(userId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto clearCart(Long userId) {
        validateUserId(userId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        shoppingCartItemService.removeAllItemsByShoppingCartId(shoppingCart.getId());
        shoppingCart.setTotalPrice(0);

        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public double getTotalPrice(Long userId) {
        validateUserId(userId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getTotalPrice();
    }

    @Override
    public int getProductCount(Long userId) {
        validateUserId(userId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getItems().size();
    }

    @Override
    public boolean isProductInCart(Long userId, Long productId) {
        validateUserId(userId);
        validateProductId(productId);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found for user ID: " + userId));

        return shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), productId);
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
    }

    private void validateProduct(Product product) {
        if (product == null || product.getId() == 0 || product.getId() <= 0) {
            throw new IllegalArgumentException("Invalid product");
        }
    }

    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
    }
}