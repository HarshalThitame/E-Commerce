package com.ecom.service;

import com.ecom.entity.ProductCategory;
import com.ecom.entity.ProductCategoryId;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {
    Optional<ProductCategory> findById(ProductCategoryId id);
    List<ProductCategory> findAll();
    ProductCategory save(ProductCategory productCategory);
    void deleteById(ProductCategoryId id);
}

