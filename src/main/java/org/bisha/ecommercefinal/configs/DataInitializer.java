package org.bisha.ecommercefinal.configs;

import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.models.*;
import org.bisha.ecommercefinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    public void init() {
        runCategory();
        runSubcategory();
        runUser();
        runProduct();
        runImage();
        runOrder();
        runShoppingCart();
        runShoppingCartItem();
        runWishlist();
        runOrderItem();
        runReview();
    }

    private void runCategory() {
        if (categoryRepository.count() > 0) return;
        else {
            categoryRepository.save(new Category(null, "Category 0", "Some description for this category", null));
        }
    }
    private void runImage() {
        if (imageRepository.count() > 0) return;
        else {
            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
            imageRepository.save(new Image(0L, "https://example.com/image.jpg", product));
        }
    }
    private void runOrderItem() {
        if (orderItemRepository.count() > 0) return;
        else {
            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            Order order = orderRepository.findById(1L).orElseThrow(() -> new RuntimeException("Order not found"));
            orderItemRepository.save(new OrderItem(null, product, user, order, 10.0));
        }
    }
    private void runOrder() {
        if (orderRepository.count() > 0) return;
        else {
            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            Order order = new Order(
                    0L,
                    user,
                    OrderStatus.PENDING,
                    100.0,
                    "123 Main St",
                    "+1234567890",
                    LocalDateTime.now(),
                    null,
                    List.of(),
                    null
            );
            orderRepository.save(order);
        }
    }
    private void runProduct() {
        if (productRepository.count() > 0) return;
        else {
            Category category = categoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Category not found"));
            Product product = new Product(
                    0L,
                    "Product 0",
                    "Some description for this product",
                    10.0,
                    List.of(),
                    List.of(),
                    100,
                    category,
                    "Brand 0",
                    4.5,
                    null,
                    true,
                    LocalDate.now()
            );
            Product product1 = new Product(
                    0L,
                    "Product 1",
                    "Some description for this product",
                    10.0,
                    List.of("products/0f81c93d-9a8d-4715-bc39-8dbe1dc300ed_pexels-madebymath-90946.jpg"),
                    List.of(),
                    100,
                    category,
                    "Brand 0",
                    4.5,
                    null,
                    true,
                    LocalDate.now()
            );
            productRepository.save(product);
            productRepository.save(product1);
        }
    }

    private void runReview() {
        if (reviewRepository.count() > 0) return;
        else {
            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            Review review = new Review(
                    0L,
                    "Great product!",
                    5,
                    product,
                    user,
                    LocalDateTime.now()
            );
            reviewRepository.save(review);
        }
    }

    private void runSubcategory() {
        if (subcategoryRepository.count() > 0) return;
        else {
            Category parentCategory = categoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Parent category not found"));
            subcategoryRepository.save(new Subcategory(null, "Subcategory 0", "Some description for this subcategory", parentCategory));
        }
    }

    private void runUser() {
        if (userRepository.count() > 0) return;
        else {
            User user = new User(
                    0L,
                    "John Doe",
                    "johndoe",
                    "johndoe@example.com",
                    "Password123",
                    Role.ROLE_USER,
                    LocalDate.of(1990, 1, 1),
                    "123 Main St",
                    "https://example.com/profile.jpg",
                    LocalDateTime.now(),
                    null,
                    "1234567890",
                    true
            );
            User user2 = new User(
                    0L,
                    "Jane Doe",
                    "janedoe",
                    "janeDoe@example.com",
                    "Password123",
                    Role.ROLE_ADMIN,
                    LocalDate.of(1990, 1, 1),
                    "123 Main St",
                    "https://example.com/profile.jpg",
                    LocalDateTime.now(),
                    null,
                    "1234567990",
                    true
            );
            userRepository.save(user);
            userRepository.save(user2);
        }
    }

    private void runShoppingCart() {
        if (shoppingCartRepository.count() > 0) return;
        else {
            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            ShoppingCart shoppingCart = new ShoppingCart(
                    null,
                    user,
                    List.of(),
                    0.0,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            shoppingCartRepository.save(shoppingCart);
        }
    }

    private void runShoppingCartItem() {
        if (shoppingCartItemRepository.count() > 0) return;
        else {
            ShoppingCart shoppingCart = shoppingCartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Shopping cart not found"));
            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
                    null,
                    shoppingCart,
                    product,
                    1,
                    10.0
            );
            shoppingCartItemRepository.save(shoppingCartItem);
        }
    }

    private void runWishlist() {
        if (wishlistRepository.count() > 0) return;
        else {
            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
            Wishlist wishlist = new Wishlist(
                    0L,
                    user,
                    List.of(product),
                    LocalDate.now()
            );
            wishlistRepository.save(wishlist);
        }
    }
}
