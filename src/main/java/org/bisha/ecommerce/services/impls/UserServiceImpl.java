package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.OrderItemDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.UserDto;
import org.bisha.ecommerce.enums.Role;
import org.bisha.ecommerce.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommerce.exceptions.ResourceNotFoundException;
import org.bisha.ecommerce.mappers.OrderItemMapper;
import org.bisha.ecommerce.mappers.UserMapper;
import org.bisha.ecommerce.models.User;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.services.UserService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, OrderItemMapper orderItemMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
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
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()){
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
        userMapper.updateEntityFromDto(userDto, user);
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
    public ProductDto buyProduct(Long userId, Long productId, int quantity) {
        return null;
    }

    @Override
    public ProductDto returnProduct(Long userId, Long productId, int quantity) {
        return null;
    }

    @Override
    public void validateUser(Long userId) {
    }

    private static void validateAvailableStock(ProductDto productDto, int quantity) {
        if (productDto.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
    }
    private static double calculateTotalPrice(HashMap<Integer, Double> map){
        double sum = 0;
        for (int i : map.keySet()) {
            sum += i * map.get(i);
        }
        return sum;
    }
}