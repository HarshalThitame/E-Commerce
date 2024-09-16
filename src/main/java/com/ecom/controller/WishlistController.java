package com.ecom.controller;

import com.ecom.entity.Wishlist;
import com.ecom.entity.WishlistItem;
import com.ecom.service.WishlistItemService;
import com.ecom.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistItemService wishlistItemService;

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        Wishlist createdWishlist = wishlistService.createWishlist(wishlist);
        return new ResponseEntity<>(createdWishlist, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Wishlist> getWishlistByUserId(@PathVariable Long userId) {
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);
        Set<WishlistItem> wishlistItemByWishlistId = wishlistItemService.getWishlistItemByWishlistId(wishlist.getId());
        wishlist.setItems(wishlistItemByWishlistId);
        if (wishlist != null) {
            return new ResponseEntity<>(wishlist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{wid}/product/{pid}")
    public ResponseEntity<Boolean> getWishlistItemByWishlistIdAndProductId(@PathVariable Long wid, @PathVariable Long pid) {

        WishlistItem wishlistItem = wishlistItemService.findByWishlistIdAndProductId(wid, pid);
        if (wishlistItem != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/{wishlistId}/items")
    public ResponseEntity<WishlistItem> addProductToWishlist(@PathVariable Long wishlistId, @RequestBody WishlistItem item) {

        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        if (wishlist != null) {
            item.setWishlist(wishlist);
            WishlistItem wishlistItem = wishlistItemService.addItem(item);
            if (wishlistItem != null) {
                return new ResponseEntity<>(wishlistItem, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        wishlistItemService.removeItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    @DeleteMapping("/items/by-wishlist/{wid}/{pid}")
    public ResponseEntity<Void> removeItemByWishlistId(@PathVariable Long wid,@PathVariable Long pid) {
        wishlistItemService.removeItemByWishlistId(wid,pid);
        return ResponseEntity.noContent().build();
    }
}
