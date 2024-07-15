package com.ecom.service;

import com.ecom.entity.CartItem;

import java.util.Optional;

public interface CartItemService {
    Optional<CartItem> findById(Long id);
    CartItem save(CartItem cartItem);
    void deleteById(Long id);
}
