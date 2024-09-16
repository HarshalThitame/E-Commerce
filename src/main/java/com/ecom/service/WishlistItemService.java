package com.ecom.service;

import com.ecom.entity.WishlistItem;

import java.util.Set;

public interface WishlistItemService {
    WishlistItem addItem(WishlistItem item);
    void removeItem(Long id);

    Set<WishlistItem> getWishlistItemByWishlistId(Long id);

    WishlistItem findByWishlistIdAndProductId(Long wid, Long pid);

    void removeItemByWishlistId(Long wid,Long pid);
}
