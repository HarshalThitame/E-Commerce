package com.ecom.repositories;

import com.ecom.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    Set<WishlistItem> findByWishlistId(Long id);

    WishlistItem findByWishlistIdAndProductId(Long wid, Long pid);

    void deleteByWishlistIdAndProductId(Long wid, Long pid);
}
