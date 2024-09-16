package com.ecom.repositories;

import com.ecom.entity.OrderItem;
import com.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderID);

    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.seller = :seller")
    Optional<List<OrderItem>> findOrderItemsBySeller(@Param("seller") User seller);

    void deleteByProductId(Long id);

    List<OrderItem> findByProductId(Long id);
}
