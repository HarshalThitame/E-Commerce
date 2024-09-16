package com.ecom.repositories;

import com.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findBySellerId(Long id);
    public Optional<Product> findByIdAndSellerUsername(Long id, String username);
    public void deleteByIdAndSellerId(Long id, Long sellerId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p JOIN p.subCategories c WHERE c.name = :categoryName")
    List<Product> findProductsBySubCategoryName(String categoryName);

    @Query("select p from Product p join p.subSubCategories c where c.name = :categoryName")
    List<Product> findProductsBySubSubCategoryName(String categoryName);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.categories c " +
            "LEFT JOIN p.subCategories sc " +
            "LEFT JOIN p.subSubCategories ssc " +
            "LEFT JOIN p.productHighlights ph " +
            "WHERE (" +
            "  LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(sc.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(ssc.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(ph.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "  OR LOWER(ph.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            ")")
    Optional<List<Product>> searchByQuery(String query);

}
