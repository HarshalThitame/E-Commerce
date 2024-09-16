package com.ecom.service;

import com.ecom.entity.Category;
import com.ecom.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    Product save(Product product);
    void deleteById(Long id);

    Product update(Long id, Product productDetails);

    List<Product> findBySellerUserId(Long sellerId);

    Optional<Product> findByIdAndSellerUsername(Long id, String sellerUsername);

    void deleteByIdAndSellerId(Long id, Long sellerId);

    public List<Product> getProductsByCategoryName(String categoryName);

    Optional<List<Category>> getCategoriesByProductId(Long id);

    Optional<List<Product>> searchProducts(String query);

}

