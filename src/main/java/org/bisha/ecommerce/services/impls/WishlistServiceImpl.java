package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.WishlistDto;
import org.bisha.ecommerce.mappers.ProductMapper;
import org.bisha.ecommerce.mappers.WishlistMapper;
import org.bisha.ecommerce.models.Wishlist;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.repositories.WishlistRepository;
import org.bisha.ecommerce.services.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, WishlistMapper wishlistMapper, UserRepository userRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMapper = wishlistMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public WishlistDto createWishlist(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    public WishlistDto getWishlistByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    public ProductDto addProductToWishlist(Long wishlistId, Long productId) {
        if (wishlistId == null || productId == null) {
            throw new IllegalArgumentException("Wishlist ID and Product ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (wishlist.getProducts().contains(product)) {
            throw new IllegalArgumentException("Product is already in the wishlist");
        }
        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto removeProductFromWishlist(Long wishlistId, Long productId) {
        if (wishlistId == null || productId == null) {
            throw new IllegalArgumentException("Wishlist ID and Product ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!wishlist.getProducts().contains(product)) {
            throw new IllegalArgumentException("Product is not in the wishlist");
        }
        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
        return productMapper.toDto(product);
    }

    @Override
    public WishlistDto clearWishlist(Long wishlistId) {
        if (wishlistId == null) {
            throw new IllegalArgumentException("Wishlist ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        if (wishlist.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Wishlist is already empty");
        }
        wishlist.getProducts().clear();
        wishlistRepository.save(wishlist);
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    public List<ProductDto> getAllProductsInWishlist(Long wishlistId) {
        if (wishlistId == null) {
            throw new IllegalArgumentException("Wishlist ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        return productMapper.toDtos(wishlist.getProducts());
    }

    @Override
    public boolean isProductInWishlist(Long wishlistId, Long productId) {
        if (wishlistId == null || productId == null) {
            throw new IllegalArgumentException("Wishlist ID and Product ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return wishlist.getProducts().contains(product);
    }

    @Override
    public WishlistDto duplicateWishlist(Long wishlistId, Long userId) {
        if (wishlistId == null || userId == null) {
            throw new IllegalArgumentException("Wishlist ID and User ID cannot be null");
        }
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var newWishlist = new Wishlist();
        newWishlist.setUser(user);
        newWishlist.setProducts(wishlist.getProducts());
        wishlistRepository.save(newWishlist);
        return wishlistMapper.toDto(newWishlist);
    }
}