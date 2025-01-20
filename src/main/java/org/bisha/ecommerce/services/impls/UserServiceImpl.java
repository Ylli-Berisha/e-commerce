package org.bisha.ecommerce.services.impls;

import jakarta.transaction.Transactional;
import org.bisha.ecommerce.dtos.*;
import org.bisha.ecommerce.enums.OrderStatus;
import org.bisha.ecommerce.enums.Role;
import org.bisha.ecommerce.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommerce.exceptions.ResourceNotFoundException;
import org.bisha.ecommerce.exceptions.WrongPasswordException;
import org.bisha.ecommerce.mappers.OrderItemMapper;
import org.bisha.ecommerce.mappers.UserMapper;
import org.bisha.ecommerce.models.OrderItem;
import org.bisha.ecommerce.models.User;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.services.OrderItemService;
import org.bisha.ecommerce.services.OrderService;
import org.bisha.ecommerce.services.ProductService;
import org.bisha.ecommerce.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, OrderItemMapper orderItemMapper, ProductService productService, OrderService orderService, ProductRepository productRepository, OrderItemService orderItemService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
        this.productService = productService;
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    public UserDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getUsersByRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Invalid role");
        }
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with the given role");
        }
        return userMapper.toDtos(users);
    }

    @Override
    public List<UserDto> getUsersByActivity(boolean active) {
        List<User> users = userRepository.findByActive(active);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with the given activity status");
        }
        return userMapper.toDtos(users);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userDto == null || userDto.getName() == null || userDto.getEmail() == null || userDto.getUsername() == null) {
            throw new IllegalArgumentException("Invalid user details");
        }
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //userMapper.updateEntityFromDto(userDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(newPassword); // Ensure to hash the password before saving
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.isActive()) {
            throw new IllegalArgumentException("User is already active");
        }
        user.setActive(true);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isActive()) {
            throw new IllegalArgumentException("User is already inactive");
        }
        user.setActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto resetPassword(String email, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(newPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<OrderItemDto> getBoughtProductsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderItemMapper.toDtos(user.getBoughtProducts());
    }

    @Override
    @Transactional
    public ProductDto buyProduct(Long userId, Long productId, int quantity) {
        // Validate user
        validateUser(userId);

        // Validate product
        ProductDto productDto = productService.getProductById(productId);
        validateAvailableStock(productDto, quantity);

        // Calculate total price
        double totalPrice = productDto.getPrice() * quantity;

        // Update stock
        productDto.setStock(productDto.getStock() - quantity);
        productService.updateProductById(productId, productDto);

        // Create order
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setOrderDate(LocalDateTime.now());

        // Save order to get the ID
        orderDto = orderService.createOrder(orderDto);

        // Create order item
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(productId);
        orderItemDto.setPrice(totalPrice);
        orderItemDto.setUserId(userId);
        orderItemDto.setQuantity(quantity);
        orderItemDto.setOrderId(orderDto.getId());

        // Save order item
        orderItemService.createOrderItem(orderItemDto);

        // Add order item to order
        orderDto.getOrderItemIds().add(orderItemDto.getId());

        // Update user bought products
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getBoughtProducts().add(orderItemMapper.toEntity(orderItemDto));

        orderDto.setTotalAmount(totalPrice);

        orderService.updateOrder(orderDto.getId(), orderDto);

        return productDto;
    }

    @Override
    @Transactional
    public List<ProductDto> buyProducts(Long userId, HashMap<Long, Integer> productIdsAndQuantities) {
        // Step 1: Validate user
        validateUser(userId);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Step 2: Initialize the order
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto = orderService.createOrder(orderDto); // Save to get ID

        // List to keep track of purchased products
        List<ProductDto> purchasedProducts = new ArrayList<>();

        // Step 3: Process each product in the HashMap
        for (Map.Entry<Long, Integer> entry : productIdsAndQuantities.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            // Validate product and stock
            ProductDto productDto = productService.getProductById(productId);
            validateAvailableStock(productDto, quantity);

            // Calculate total price for this product
            double totalPrice = productDto.getPrice() * quantity;

            // Update stock
            productDto.setStock(productDto.getStock() - quantity);
            productService.updateProductById(productId, productDto);

            // Create order item
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(productId);
            orderItemDto.setPrice(totalPrice);
            orderItemDto.setUserId(userId);
            orderItemDto.setQuantity(quantity);
            orderItemDto.setOrderId(orderDto.getId());

            // Save order item
            orderItemService.createOrderItem(orderItemDto);

            // Add the created order item to the order
            orderDto.getOrderItemIds().add(orderItemDto.getId());

            orderDto.setTotalAmount(orderDto.getTotalAmount() + orderItemDto.getPrice());

            // Add order item to user's bought products
            user.getBoughtProducts().add(orderItemMapper.toEntity(orderItemDto));

            // Add product to the list of purchased products
            purchasedProducts.add(productDto);
        }

        // Save user
        userRepository.save(user);


        orderService.updateOrder(orderDto.getId(), orderDto);

        // Step 5: Return the list of purchased products
        return purchasedProducts;
    }

    @Override
    @Transactional
    public ProductDto returnProduct(Long userId, Long productId, int quantity) {
        validateUser(userId);
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        List<Long> orderItemIds = orders.stream()
                .flatMap(orderDto -> orderDto.getOrderItemIds().stream())
                .collect(Collectors.toList());
        boolean isProductBought = false;
        Long orderId = null;
        for (Long id : orderItemIds) {
            OrderItemDto orderItem = orderItemService.getOrderItemById(id);
            if (orderItem.getProductId().equals(productId)) {
                isProductBought = true;
                orderId = orderItem.getOrderId();
                break;
            }
        }
        if (!isProductBought) {
            throw new IllegalArgumentException("Product not bought by user");
        }
        ProductDto productDto = productService.getProductById(productId);
        productDto.setStock(productDto.getStock() + quantity);
        productService.updateProductById(productId, productDto);
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getBoughtProducts().remove(orderItemMapper.toEntity(orderItemService.getOrderItemById(productId)));
        userRepository.save(user);

        OrderDto orderDto = orderService.getOrderById(orderId);
        orderDto.setStatus(OrderStatus.RETURNED);
        orderService.updateOrder(orderId, orderDto);


        return productDto;
    }


    @Override
    public void validateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isActive()) {
            throw new IllegalArgumentException("User is not active");
        }
        if (user.getRole() != Role.ROLE_USER) {
            throw new IllegalArgumentException("User is not a customer");
        }
    }


    private static void validateAvailableStock(ProductDto productDto, int quantity) {
        if (productDto.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
    }

//    private static double calculateTotalPrice(HashMap<Integer, Double> map) {
//        double sum = 0;
//        for (int i : map.keySet()) {
//            sum += i * map.get(i);
//        }
//        return sum;
//    }

    @Override
    public UserLoginDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getPassword().equals(userLoginDto.getPassword())) {
            return new UserLoginDto(user.getUsername(), userLoginDto.getPassword());
        } else {
            throw new WrongPasswordException("Invalid password");
        }
    }

    @Override
    public UserRegisterDto register(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setName(userRegisterDto.getName());
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        user.setRole(userRegisterDto.getRole());
        user.setBirthDate(userRegisterDto.getBirthDate());
        user.setAddress(userRegisterDto.getAddress());
        user.setProfilePictureURL(userRegisterDto.getProfilePictureURL());
        user.setTelephoneNumber(userRegisterDto.getTelephoneNumber());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return userRegisterDto;
    }
}