package com.ecom.service.impl;

import com.ecom.entity.WishlistItem;
import com.ecom.repositories.WishlistItemRepository;
import com.ecom.service.WishlistItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WishlistItemServiceImpl implements WishlistItemService {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Override
    public WishlistItem addItem(WishlistItem item) {
        return wishlistItemRepository.save(item);
    }

    @Override
    public void removeItem(Long id) {
        wishlistItemRepository.deleteById(id);
    }

    @Override
    public Set<WishlistItem> getWishlistItemByWishlistId(Long id) {
        return wishlistItemRepository.findByWishlistId(id);
    }

    @Override
    public WishlistItem findByWishlistIdAndProductId(Long wid, Long pid) {
        return wishlistItemRepository.findByWishlistIdAndProductId(wid,pid);
    }

    @Transactional
    @Override
    public void removeItemByWishlistId(Long wid,Long pid) {
        wishlistItemRepository.deleteByWishlistIdAndProductId(wid,pid);
    }
}
