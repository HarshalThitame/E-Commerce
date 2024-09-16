package com.ecom.repository;

import com.ecom.entity.ReviewAndRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewAndRatingRepository extends JpaRepository<ReviewAndRating, Long> {

    // Find reviews by product ID
    Set<ReviewAndRating> findByProductId(Long productId);

    // Find reviews by user ID
    Optional<Set<ReviewAndRating>> findByUserId(Long userId);
}
