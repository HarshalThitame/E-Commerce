package com.ecom.service.impl;

import com.ecom.entity.ReviewAndRating;
import com.ecom.service.ReviewAndRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ReviewAndRatingServiceImpl implements ReviewAndRatingService {

    @Autowired
    private com.ecom.repository.ReviewAndRatingRepository reviewAndRatingRepository;

    @Override
    public Optional<ReviewAndRating> saveReview(ReviewAndRating reviewAndRating) {
        return Optional.of(reviewAndRatingRepository.save(reviewAndRating));
    }

    @Override
    public Set<ReviewAndRating> getReviewsByProductId(Long productId) {
        return reviewAndRatingRepository.findByProductId(productId);
    }

    @Override
    public Optional<Set<ReviewAndRating>> getReviewsByUserId(Long userId) {
        return reviewAndRatingRepository.findByUserId(userId);
    }

    @Override
    public Optional<ReviewAndRating> getReviewById(Long reviewId) {
        return reviewAndRatingRepository.findById(reviewId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewAndRatingRepository.deleteById(reviewId);
    }
}
