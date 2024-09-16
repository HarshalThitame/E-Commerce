package com.ecom.service.impl;

import com.ecom.entity.Category;
import com.ecom.entity.Image;
import com.ecom.entity.Product;
import com.ecom.repositories.*;
import com.ecom.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductHighlightsRepository productHighlightsRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private com.ecom.repository.ReviewAndRatingRepository ReviewAndRatingRepository;

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final SubCategoryRepository subCategoryRepository;
    @Autowired
    private final SubSubCategoryRepository subSubCategoryRepository;
    @Autowired
    private com.ecom.repository.ReviewAndRatingRepository reviewAndRatingRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              SubCategoryRepository subCategoryRepository, SubSubCategoryRepository subSubCategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.subSubCategoryRepository = subSubCategoryRepository;
    }

    @Transactional
    @Override
    public Optional<Product> findById(Long id) {


        Optional<Product> product = productRepository.findById(id);
//        List<Category> categories = product.get().getCategories();
//        List<SubCategory> subCategories = product.get().getSubCategories();
//        List<SubSubCategory> subSubCategories = product.get().getSubSubCategories();
//
//        product.get().setCategories(null);
//        product.get().setSubCategories(null);
//        product.get().setSubSubCategories(null);
//
//


        return product;

    }

    @Override
    public List<Product> findAll() {
        List<Product> allProducts = productRepository.findAll();


        for (Product product : allProducts) {
            Set<Image> images = imageRepository.findByProductId(product.getId());
            product.setImages(images);
        }
        // Filter products based on publication status and non-empty images
        List<Product> filteredProducts = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getPublished() != null && p.getPublished() && p.getImages() != null && !p.getImages().isEmpty()) {
                filteredProducts.add(p);
            }
        }

        Collections.shuffle(filteredProducts);
        return filteredProducts.stream().limit(120).toList();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // Find the product by id
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        Product product = optionalProduct.get();

        // Remove product from categories, subCategories, and subSubCategories
        product.getCategories().forEach(category -> category.getProducts().remove(product));
        product.getSubCategories().forEach(subCategory -> subCategory.getProducts().remove(product));
        product.getSubSubCategories().forEach(subSubCategory -> subSubCategory.getProducts().remove(product));

        // Remove related ProductHighlights and Images
        product.getProductHighlights().forEach(highlight -> highlight.setProduct(null));
        product.getImages().forEach(image -> image.setProduct(null));

        product.setCategories(null);
        product.setSubCategories(null);
        product.setSubSubCategories(null);

        // Delete the product
        productRepository.delete(product);

    }

    @Override
    public Product update(Long id, Product productDetails) {
        return productRepository.save(productDetails);
    }

    @Override
    public List<Product> findBySellerUserId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public Optional<Product> findByIdAndSellerUsername(Long id, String sellerUsername) {
        return productRepository.findByIdAndSellerUsername(id, sellerUsername);
    }

    @Override
    public void deleteByIdAndSellerId(Long id, Long sellerId) {

        productRepository.deleteByIdAndSellerId(id, sellerId);
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findProductsByCategoryName(categoryName);
        if (products.isEmpty()) {
            products = productRepository.findProductsBySubCategoryName(categoryName);
            if (products.isEmpty()) {
                products = productRepository.findProductsBySubSubCategoryName(categoryName);
            }
        }

        // Fetch and set images for each product
        for (Product product : products) {
            Set<Image> images = imageRepository.findByProductId(product.getId());
            product.setImages(images);
        }

        return products;
    }


    @Override
    public Optional<List<Category>> getCategoriesByProductId(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(Product::getCategories);
    }

    @Override
    public Optional<List<Product>> searchProducts(String keyword) {

        String[] keywords = keyword.split("\\s+");

        String searchQuery = Stream.of(keywords)
                .map(String::trim) // Remove extra spaces
                .filter(k -> !k.isEmpty()) // Ignore empty strings
                .map(k -> "%" + k + "%") // Add '%' for LIKE condition
                .collect(Collectors.joining(" ")); // Join them with a space for LIKE condition


        // Retrieve the products based on the search query
        Optional<List<Product>> optionalProducts = productRepository.searchByQuery(searchQuery);

        // If no products are found, return an empty Optional
        if (optionalProducts.isEmpty()) {
            return Optional.of(Collections.emptyList());
        }

        // Get the list of products
        List<Product> products = optionalProducts.get();
        List<Product> filteredProducts = new ArrayList<>();

        // Process each product
        for (Product product : products) {
            // Retrieve images for the product
            Set<Image> images = imageRepository.findByProductId(product.getId());
            product.setImages(images);

            // Check if the product is published and has images
            if (product.getPublished() != null && product.getPublished() && images != null && !images.isEmpty()) {
                filteredProducts.add(product);
            }
        }

        // Return the filtered list of products wrapped in an Optional
        return Optional.of(filteredProducts);
    }

}

