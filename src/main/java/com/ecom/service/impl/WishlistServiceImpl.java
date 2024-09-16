package com.ecom.service.impl;

import com.ecom.entity.Wishlist;
import com.ecom.entity.WishlistItem;
import com.ecom.repositories.WishlistItemRepository;
import com.ecom.repositories.WishlistRepository;
import com.ecom.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public void addProductToWishlist(Long wishlistId, WishlistItem item) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow();
        item.setWishlist(wishlist);
        wishlist.getItems().add(item);
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow();
        wishlist.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId).orElseThrow();
    }
}
