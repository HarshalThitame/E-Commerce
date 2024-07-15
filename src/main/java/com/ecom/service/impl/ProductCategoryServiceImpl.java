package com.ecom.service.impl;

import com.ecom.entity.ProductCategory;
import com.ecom.entity.ProductCategoryId;
import com.ecom.repositories.ProductCategoryRepository;
import com.ecom.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public Optional<ProductCategory> findById(ProductCategoryId id) {
        return productCategoryRepository.findById(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteById(ProductCategoryId id) {
        productCategoryRepository.deleteById(id);
    }
}
