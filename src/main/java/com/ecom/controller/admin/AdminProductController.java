package com.ecom.controller.admin;

import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;
//
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productService.findAll();
//        return ResponseEntity.ok(products);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Optional<Product> product = productService.findById(id);
//        if (!product.isPresent()) {
//            throw new ResourceNotFoundException("Product not found with id: " + id);
//        }
//        return ResponseEntity.ok(product.get());
//    }

//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        Product newProduct = productService.save(product);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
//        Product updatedProduct = productService.update(id, productDetails);
//        return ResponseEntity.ok(updatedProduct);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}

