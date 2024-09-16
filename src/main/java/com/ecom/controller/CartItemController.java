package com.ecom.controller;

import com.ecom.entity.CartItem;
import com.ecom.entity.Image;
import com.ecom.entity.Product;
import com.ecom.service.CartItemService;
import com.ecom.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ImageService imageService;

//    @GetMapping("/{id}")
//    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
//        Optional<CartItem> cartItem = cartItemService.findById(id);
//        if (cartItem.isEmpty()) {
//            throw new ResourceNotFoundException("CartItem not found with id: " + id);
//        }
//        return ResponseEntity.ok(cartItem.get());
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUser(@PathVariable Long userId) {
        List<CartItem> items = cartItemService.getCartItemsByUserId(userId);


        for(CartItem item : items) {
            Product product = item.getProduct();
            Set<Image> images = imageService.getImagesByProductId(product.getId());
            product.setImages(images);
            item.setProduct(product);
        }

        if (items.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(items);
    }

    @PutMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem newCartItem = cartItemService.save(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCartItem);
    }

    @GetMapping("/check-product/{productId}")
    public ResponseEntity<Map<String, Object>> checkProductInCart(@PathVariable Long productId) {
        Optional<CartItem> cartItem = cartItemService.findByProductInCurrentCart(productId);
        Map<String, Object> response = new HashMap<>();
        if (cartItem.isPresent()) {
            response.put("exists", true);
            response.put("itemId", cartItem.get().getId());
        } else {
            response.put("exists", false);
        }
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{itemId}/increase")
    public ResponseEntity<Map<String, Object>> increaseQuantity(@PathVariable Long itemId) {
        Optional<CartItem> cartItemOptional = cartItemService.findById(itemId);
        if (cartItemOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "CartItem not found"));
        }

        CartItem cartItem = cartItemOptional.get();
        Product product = cartItem.getProduct();
        int currentQuantity = cartItem.getQuantity();
        int stockQuantity = product.getStockQuantity();

        if (stockQuantity <= currentQuantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Insufficient stock"));
        }

        cartItem.setQuantity(currentQuantity + 1);
        cartItemService.save(cartItem);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("cartItem", cartItem);
        response.put("stockQuantity", stockQuantity - 1);  // Updated stock quantity after the increase
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{itemId}/decrease")
    public ResponseEntity<Void> decreaseQuantity(@PathVariable Long itemId) {
        cartItemService.decreaseQuantity(itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        cartItemService.removeItem(itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/byCartId/{cartId}")
    public ResponseEntity<Void> removeItemByCartId(@PathVariable Long cartId) {
        cartItemService.deleteCartItem(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestBody List<CartItem> cartItems) {
        cartItemService.checkout(cartItems);
        return ResponseEntity.ok().build();
    }
}
