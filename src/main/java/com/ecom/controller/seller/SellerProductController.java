package com.ecom.controller.seller;

import com.ecom.config.aws.S3Service;
import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.*;
import com.ecom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/seller/products")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ProductHighlightsService productHighlightsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private SubSubCategoryService subSubCategoryService;

    public static ResponseEntity<Product> saveOrUpdateProduct(Product product, ProductService productService, CategoryService categoryService, SubCategoryService subCategoryService, SubSubCategoryService subSubCategoryService, ProductHighlightsService productHighlightsService) {
        List<ProductHighlights> productHighlights = product.getProductHighlights();
        product.setProductHighlights(null);
        product.setImages(null);

        // Handle categories - attach existing categories
        List<Category> categories = new ArrayList<>();
        for (Category category : product.getCategories()) {
            Category existingCategory = categoryService.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            categories.add(existingCategory);  // Use the managed entity
        }
        product.setCategories(categories);

        // Handle subcategories similarly
        List<SubCategory> subCategories = new ArrayList<>();
        for (SubCategory subCategory : product.getSubCategories()) {
            SubCategory existingSubCategory = subCategoryService.findById(subCategory.getId())
                    .orElseThrow(() -> new RuntimeException("SubCategory not found"));
            subCategories.add(existingSubCategory);  // Use the managed entity
        }
        product.setSubCategories(subCategories);

        //Handle subsubcategory
        List<SubSubCategory> subSubCategories = new ArrayList<>();
        for (SubSubCategory subSubCategory : product.getSubSubCategories()) {
            SubSubCategory existingSubSubCategory = subSubCategoryService.findById(subSubCategory.getId())
                    .orElseThrow(() -> new RuntimeException("SubSubCategory not found"));
            subSubCategories.add(existingSubSubCategory);
        }
        product.setSubSubCategories(subSubCategories);

        // Save product
        Product savedProduct = productService.save(product);

        // Additional logic for productHighlights
        List<ProductHighlights> newProductHighlights = new ArrayList<>();
        if (productHighlights != null) {
            for (ProductHighlights ph : productHighlights) {
                ph.setProduct(savedProduct);
                ProductHighlights savedProductHighlight = productHighlightsService.saveProductHighlights(ph);
                newProductHighlights.add(savedProductHighlight);
            }
        }
        savedProduct.setProductHighlights(new ArrayList<>(newProductHighlights));

        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        Optional<Product> product = productService.findById(id);

        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/bySellerId/{id}")
    public ResponseEntity<List<Product>> getProductBySellerId(@PathVariable Long id) {
        List<Product> product = productService.findBySellerUserId(id);
        for (Product p : product) {
            Set<Image> images = imageService.getImagesByProductId(p.getId());
            p.setImages(images);
        }
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return saveOrUpdateProduct(product,productService,categoryService,subCategoryService,subSubCategoryService,productHighlightsService);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> product1 = productService.findById(id);
        product.setId(id);
        if (product1.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        product.setCreatedAt(product1.get().getCreatedAt());
//        product.setPublished(product1.get().getPublished());
        return saveOrUpdateProduct(product,productService,categoryService,subCategoryService,subSubCategoryService,productHighlightsService);
    }

    @PutMapping("/published/{id}")
    public ResponseEntity<Product> publishProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> product1 = productService.findById(id);
        product.setId(id);
        if (product1.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        product1.get().setPublished(product.getPublished());
        return saveOrUpdateProduct(product1.get(),productService,categoryService,subCategoryService,subSubCategoryService,productHighlightsService);
    }



    @PostMapping("/images")
    public ResponseEntity<?> uploadImage(@RequestBody List<String> images) {


        String imageName = images.get(0);
        String[] arrayOfName = imageName.split("_");
        String productId = arrayOfName[0];

        Product product = productService.findById(Long.parseLong(productId)).orElseThrow();

        for (String name : images) {
            Image image = new Image();
            image.setProduct(product);
            image.setUrl(name);
            imageService.saveImage(image);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
