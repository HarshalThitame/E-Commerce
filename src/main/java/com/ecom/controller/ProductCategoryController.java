package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.ProductCategory;
import com.ecom.entity.ProductCategoryId;
import com.ecom.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/{productId}/{categoryId}")
    public ResponseEntity<ProductCategory> getProductCategoryByIds(@PathVariable Long productId, @PathVariable Long categoryId) {
        ProductCategoryId ids = new ProductCategoryId(productId, categoryId);
        Optional<ProductCategory> productCategory = productCategoryService.findById(ids);
        if (productCategory.isEmpty()) {
            throw new ResourceNotFoundException("ProductCategory not found with ids: productId=" + productId + ", categoryId=" + categoryId);
        }
        return ResponseEntity.ok(productCategory.get());
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.findAll();
        return ResponseEntity.ok(productCategories);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory newProductCategory = productCategoryService.save(productCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductCategory);
    }

    @DeleteMapping("/{productId}/{categoryId}")
    public ResponseEntity<Void> deleteProductCategoryByIds(@PathVariable Long productId, @PathVariable Long categoryId) {
        ProductCategoryId ids = new ProductCategoryId(productId, categoryId);
        productCategoryService.deleteById(ids);
        return ResponseEntity.noContent().build();
    }
}

