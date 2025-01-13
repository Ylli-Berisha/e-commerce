package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.ShoppingCartDto;
import org.bisha.ecommerce.dtos.ShoppingCartItemDto;
import org.bisha.ecommerce.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommerce.exceptions.ResourceNotFoundException;
import org.bisha.ecommerce.mappers.ProductDtoToShoppingCartItemMapper;
import org.bisha.ecommerce.mappers.ShoppingCartItemMapper;
import org.bisha.ecommerce.mappers.ShoppingCartMapper;
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
    private final ProductDtoToShoppingCartItemMapper productToShoppingCartItemMapper;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper, ProductDtoToShoppingCartItemMapper productToShoppingCartItemMapper, ShoppingCartItemService shoppingCartItemService, ShoppingCartItemMapper shoppingCartItemMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.productToShoppingCartItemMapper = productToShoppingCartItemMapper;
        this.shoppingCartItemService = shoppingCartItemService;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
    }

    @Override
    public ShoppingCartDto addProductToCart(Long userId, ProductDto product) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        if (shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), product.getId())) {
            throw new ResourceAlreadyExistsException("Product already exists in cart");
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
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        ShoppingCartItemDto removedItem = shoppingCartItemService.removeItemByShoppingCartIdAndProductId(shoppingCart.getId(), productId);

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - removedItem.getPrice());
        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getCartByUserId(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto clearCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        shoppingCartItemService.removeAllItemsByShoppingCartId(shoppingCart.getId());
        shoppingCart.setTotalPrice(0);

        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public double getTotalPrice(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getTotalPrice();
    }

    @Override
    public int getProductCount(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getItems().size();
    }

    @Override
    public boolean isProductInCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), productId);
    }
}