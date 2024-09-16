package com.ecom.service;

import com.ecom.entity.ReviewAndRating;

import java.util.Optional;
import java.util.Set;

public interface ReviewAndRatingService {

    // Save or update a review
    public Optional<ReviewAndRating> saveReview(ReviewAndRating reviewAndRating);

    // Get all reviews for a specific product
    public Set<ReviewAndRating> getReviewsByProductId(Long productId);

    // Get all reviews by a specific user
    public Optional<Set<ReviewAndRating>> getReviewsByUserId(Long userId);

    // Get a specific review by ID
    public Optional<ReviewAndRating> getReviewById(Long reviewId);

    // Delete a review by ID
    public void deleteReview(Long reviewId);
}
