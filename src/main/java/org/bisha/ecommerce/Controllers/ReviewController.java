package org.bisha.ecommerce.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.bisha.ecommerce.dtos.ReviewDto;
import org.bisha.ecommerce.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{productId}")
    public ReviewDto addReview(@PathVariable @Min(1) Long productId,
                               @Valid @RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(productId, reviewDto);
    }

    @GetMapping("/{productId}")
    public List<ReviewDto> getReviewsByProductId(@PathVariable @Min(1) Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PutMapping("/{reviewId}")
    public ReviewDto updateReview(@PathVariable @Min(1) Long reviewId,
                                  @Valid @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(reviewId, reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ReviewDto deleteReview(@PathVariable @Min(1) Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }

    @GetMapping("/review/{reviewId}")
    public ReviewDto getReviewById(@PathVariable @Min(1) Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewDto> getReviewsByUserId(@PathVariable @Min(1) Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/average-rating/{productId}")
    public double getAverageRatingForProduct(@PathVariable @Min(1) Long productId) {
        return reviewService.getAverageRatingForProduct(productId);
    }

    @GetMapping("/get-dto")
    public ReviewDto getDto() {
        return new ReviewDto();
    }

}