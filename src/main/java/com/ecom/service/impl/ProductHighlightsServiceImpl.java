package com.ecom.service.impl;

import com.ecom.entity.ProductHighlights;
import com.ecom.repositories.ProductHighlightsRepository;
import com.ecom.service.ProductHighlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductHighlightsServiceImpl implements ProductHighlightsService {

    @Autowired
    private ProductHighlightsRepository productHighlightsRepository;

    @Override
    public ProductHighlights saveProductHighlights(ProductHighlights productHighlights) {
        return productHighlightsRepository.save(productHighlights);
    }

    @Override
    public Optional<ProductHighlights> getProductHighlightsById(long id) {
        return productHighlightsRepository.findById(id);
    }

    @Override
    public Optional<List<ProductHighlights>> getProductHighlightsByProductId(long productId) {
        return productHighlightsRepository.findByProductId(productId);
    }

    @Override
    public void deleteProductHighlightById(Long id) {
        productHighlightsRepository.deleteById(id);
    }
}
