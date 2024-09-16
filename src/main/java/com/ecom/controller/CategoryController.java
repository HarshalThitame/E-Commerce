package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.entity.SubCategory;
import com.ecom.entity.SubSubCategory;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
import com.ecom.service.SubCategoryService;
import com.ecom.service.SubSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private SubSubCategoryService subSubCategoryService;

    @Autowired
    private ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(category.get());
    }

    @GetMapping("/by-product/{id}")
    public ResponseEntity<List<Category>> getCategoryByProductId(@PathVariable Long id) {

        Optional<Product> product = productService.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        return ResponseEntity.ok().body(product.get().getCategories());

    }

    @GetMapping("/sub-category/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        Optional<SubCategory> subCategory = subCategoryService.findById(id);
        if (subCategory.isEmpty()) {
            throw new ResourceNotFoundException("SubCategory not found with id: " + id);
        }
        return ResponseEntity.ok(subCategory.get());
    }

    @GetMapping("/sub-sub-category/{id}")
    public ResponseEntity<SubSubCategory> getSubSubCategoryById(@PathVariable Long id) {
        Optional<SubSubCategory> subSubCategory = subSubCategoryService.findById(id);
        if (subSubCategory.isEmpty()) {
            throw new ResourceNotFoundException("SubSubCategory not found with id: " + id);
        }
        return ResponseEntity.ok(subSubCategory.get());
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        Category save = categoryService.save(category);


        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
//@PostMapping
//public ResponseEntity<Category> addCategoryWithSubcategories(@RequestBody Category category) {
//    try {
//        Category savedCategory = categoryService.addCategoryWithSubcategories(category);
//        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
//    } catch (Exception e) {
//        // Log the exception
//        // e.g., logger.error("Error adding category", e);
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
