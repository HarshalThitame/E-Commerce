package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.*;
import com.ecom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/general")
public class GeneralController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductHighlightsService productHighlightsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private ReviewAndRatingService reviewAndRatingService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/sub-categories/{name}")
    public ResponseEntity<List<SubCategory>> getSubCategories(@PathVariable String name) {

        Category category = categoryService.findByName(name);
        List<SubCategory> subCategories = category.getSubCategories();
        return ResponseEntity.ok(subCategories);
    }
    @GetMapping("/sub-category/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        Optional<SubCategory> subCategory = subCategoryService.findById(id);
        if (subCategory.isEmpty()) {
            throw new ResourceNotFoundException("SubCategory not found with id: " + id);
        }
        return ResponseEntity.ok(subCategory.get());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        Set<Image> images = imageService.getImagesByProductId(id);
        List<ProductHighlights> productHighlights = productHighlightsService.getProductHighlightsByProductId(id).get();
        Product product1 = product.get();
        product1.setImages(images);
        product1.setProductHighlights(productHighlights);
//
//        Optional<Set<ReviewAndRating>> reviewAndRatings = reviewAndRatingService.getReviewsByProductId(product.get().getId());
//        product1.setReviewsAndRatings(reviewAndRatings.get());


        return ResponseEntity.ok(product1);
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String categoryName) {
        // Fetch products by category name from the service
        List<Product> products = productService.getProductsByCategoryName(categoryName);

        // Check if no products are found and return 404
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Filter products based on publication status and non-empty images
        List<Product> filteredProducts = new ArrayList<>();
        for (Product p : products) {
            if (p.getPublished() != null && p.getPublished() && p.getImages() != null && !p.getImages().isEmpty()) {
                filteredProducts.add(p);
            }
        }

//        Collections.shuffle(filteredProducts);

        // Return filtered products with 200 OK status
        return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductsBySearch(@RequestParam String query) {
        Optional<List<Product>> products = productService.searchProducts(query);
        if(products.isEmpty())
        {
            throw new ResourceNotFoundException("No products found with query: " + query);
        }
        return new ResponseEntity<>(products.get(), HttpStatus.OK);
    }

    @GetMapping("/images/{productId}")
    public ResponseEntity<Set<Image>> getImagesByProductId(@PathVariable Long productId) {
        Set<Image> images = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(Objects.requireNonNullElseGet(images, HashSet::new));
    }



}
