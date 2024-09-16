package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.Product;
import com.ecom.entity.ProductHighlights;
import com.ecom.service.ProductHighlightsService;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductHighlightsService productHighlightsService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return ResponseEntity.ok(product.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return getProductResponseEntity(id, product, productService, productHighlightsService);
    }


    public static ResponseEntity<Product> getProductResponseEntity(@PathVariable Long id, @RequestBody Product product, ProductService productService, ProductHighlightsService productHighlightsService) {
        Optional<Product> productOptional = productService.findById(id);
        List<ProductHighlights> productHighlights = product.getProductHighlights();
        product.setProductHighlights(null);
        product.setImages(null);


        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImages(product.getImages());
            existingProduct.setStockQuantity(product.getStockQuantity());
            existingProduct.setPublished(product.getPublished());
            existingProduct.setProductHighlights(product.getProductHighlights());
            existingProduct.setDiscount(product.getDiscount());

            Product savedProduct = productService.save(existingProduct);

            List<ProductHighlights> newProductHighlights = new ArrayList<>();
            for (ProductHighlights p : productHighlights) {

                p.setProduct(savedProduct);
                ProductHighlights saveProductHighlights = productHighlightsService.saveProductHighlights(p);
                newProductHighlights.add(saveProductHighlights);
            }
            savedProduct.setProductHighlights(newProductHighlights);

            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
