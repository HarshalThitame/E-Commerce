package com.ecom.service;

import com.ecom.entity.ProductHighlights;

import java.util.List;
import java.util.Optional;

public interface ProductHighlightsService {
    public ProductHighlights saveProductHighlights(ProductHighlights productHighlights);
    public Optional<ProductHighlights> getProductHighlightsById(long id);
    public Optional<List<ProductHighlights>> getProductHighlightsByProductId(long productId);

    void deleteProductHighlightById(Long id);
}
