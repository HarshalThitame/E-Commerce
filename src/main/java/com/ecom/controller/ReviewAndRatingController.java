package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.ReviewAndRating;
import com.ecom.repositories.ProductRepository;
import com.ecom.service.ReviewAndRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/general/reviews")
public class ReviewAndRatingController {

    @Autowired
    private ReviewAndRatingService reviewAndRatingService;
    @Autowired
    private ProductRepository productRepository;

    // Add or update a review
    @PostMapping
    public ResponseEntity<ReviewAndRating> addReview(@RequestBody ReviewAndRating reviewAndRating) {
        Optional<ReviewAndRating> savedReview = reviewAndRatingService.saveReview(reviewAndRating);
        if(savedReview.isEmpty())
        {
            throw new ResourceNotFoundException("Error while adding review"+ reviewAndRating);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview.get());
    }
    // Add or update a review
    @PutMapping()
    public ResponseEntity<ReviewAndRating> updateReview(@RequestBody ReviewAndRating reviewAndRating) {
        if(reviewAndRating.getId() == null){
            throw new ResourceNotFoundException("id is null");
        }
        else{
            reviewAndRatingService.saveReview(reviewAndRating);
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewAndRating);
    }

    // Get all reviews for a specific product
    @GetMapping("/product/{productId}")
    public ResponseEntity<Set<ReviewAndRating>> getReviewsByProductId(@PathVariable Long productId) {
        Set<ReviewAndRating> reviews = reviewAndRatingService.getReviewsByProductId(productId);
        if (reviews.isEmpty()) {
            HashSet<ReviewAndRating> reviewAndRatings = new HashSet<ReviewAndRating>();
            return ResponseEntity.status(HttpStatus.OK).body(reviewAndRatings);
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    // Get all reviews by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<ReviewAndRating>> getReviewsByUserId(@PathVariable Long userId) {
        Optional<Set<ReviewAndRating>> reviews = reviewAndRatingService.getReviewsByUserId(userId);
        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("Error while getting reviews by user id "+ userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviews.get());
    }



    // Get a specific review by ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewAndRating> getReviewById(@PathVariable Long reviewId) {
        Optional<ReviewAndRating> review = reviewAndRatingService.getReviewById(reviewId);
        if (review.isEmpty()) {
            throw new ResourceNotFoundException("Error while getting review by id "+ reviewId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(review.get());
    }

    // Delete a review by ID
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewAndRatingService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
