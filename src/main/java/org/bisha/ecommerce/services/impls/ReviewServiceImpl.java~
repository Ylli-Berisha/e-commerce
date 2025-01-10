package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.ReviewDto;
import org.bisha.ecommerce.mappers.ReviewMapper;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.repositories.ReviewRepository;
import org.bisha.ecommerce.repositories.UserRepository;
import org.bisha.ecommerce.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto addReview(Long productId, ReviewDto reviewDto) {
        if (productId == null || productId <= 0 || productId > productRepository.count()) {
            throw new IllegalArgumentException("Product ID out of bounds");
        }
        if (reviewDto == null || reviewDto.getRating() < 1 || reviewDto.getRating() > 5 ||
                reviewDto.getDescription() == null || reviewDto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Invalid review data");
        }
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        var review = reviewMapper.toEntity(reviewDto);
        review.setProduct(product);
        product.getReviews().add(review);
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDto> getReviewsByProductId(Long productId) {
        if (productId == null || productId <= 0 || productId > productRepository.count()) {
            throw new IllegalArgumentException("Product ID out of bounds");
        }
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return reviewMapper.toDtos(reviewRepository.findByProduct(product));
    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
        if (reviewId == null || reviewId <= 0 || reviewId > reviewRepository.count()) {
            throw new IllegalArgumentException("Review ID out of bounds");
        }
        if (reviewDto == null || reviewDto.getRating() < 1 || reviewDto.getRating() > 5 ||
                reviewDto.getDescription() == null || reviewDto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Invalid review data");
        }
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto deleteReview(Long reviewId) {
        if (reviewId == null || reviewId <= 0 || reviewId > reviewRepository.count()) {
            throw new IllegalArgumentException("Review ID out of bounds");
        }
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        reviewRepository.delete(review);
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto getReviewById(Long reviewId) {
        if (reviewId == null || reviewId <= 0 || reviewId > reviewRepository.count()) {
            throw new IllegalArgumentException("Review ID out of bounds");
        }
        return reviewMapper.toDto(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found")));
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        if (userId == null || userId <= 0 || userId > userRepository.count()) {
            throw new IllegalArgumentException("User ID out of bounds");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return reviewMapper.toDtos(reviewRepository.findByUser(user));
    }

    @Override
    public double getAverageRatingForProduct(Long productId) {
        if (productId == null || productId <= 0 || productId > productRepository.count()) {
            throw new IllegalArgumentException("Product ID out of bounds");
        }
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return reviewRepository.findByProduct(product).stream()
                .mapToDouble(review -> reviewMapper.toDto(review).getRating())
                .average()
                .orElse(0.0);
    }

}
