package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.Cart;
import com.ecom.entity.User;
import com.ecom.service.CartService;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts/users")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.findById(id);
        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
        return ResponseEntity.ok(cart.get());
    }

    @GetMapping("/getByUser/{id}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long id) {
        User user = userService.findById(id).get();
        Optional<Cart> cart = Optional.ofNullable(cartService.findByUser(user));
        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("Cart not found for id: " + user.getId());
        }
        return ResponseEntity.ok(cart.get());
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart newCart = cartService.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartById(@PathVariable Long id) {
        cartService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
