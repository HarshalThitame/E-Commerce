package com.ecom.repositories;

import com.ecom.entity.Cart;
import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN ci.cart c WHERE c.user.id = :userId")
    List<CartItem> findCartItemsByUserId(@Param("userId") Long userId);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteByCartId(Long id);


}
