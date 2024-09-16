package com.ecom.service;

import com.ecom.entity.Cart;
import com.ecom.entity.User;

import java.util.Optional;

public interface CartService {
    Optional<Cart> findById(Long id);
    Cart findByUser(User user);
    Cart save(Cart cart);
    void deleteById(Long id);

    Cart getCurrentCart();
}
