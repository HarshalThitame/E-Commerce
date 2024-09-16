package com.ecom.service;

import com.ecom.entity.Wishlist;
import com.ecom.entity.WishlistItem;

public interface WishlistService {
    Wishlist createWishlist(Wishlist wishlist);
    Wishlist getWishlistByUserId(Long userId);
    void addProductToWishlist(Long wishlistId, WishlistItem item);
    void removeProductFromWishlist(Long wishlistId, Long productId);

    Wishlist getWishlistById(Long wishlistId);
}
