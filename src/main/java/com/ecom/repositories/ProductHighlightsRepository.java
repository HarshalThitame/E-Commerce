package com.ecom.repositories;

import com.ecom.entity.ProductHighlights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductHighlightsRepository extends JpaRepository<ProductHighlights, Long> {
    Optional<List<ProductHighlights>> findByProductId(Long id);

    void deleteByProductId(Long id);
}
