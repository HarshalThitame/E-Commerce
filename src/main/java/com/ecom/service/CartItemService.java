package com.ecom.service;

import com.ecom.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    Optional<CartItem> findById(Long id);
    CartItem save(CartItem cartItem);
    void deleteById(Long id);
    public List<CartItem> getCartItemsByUserId(Long userId);

    public CartItem increaseQuantity(Long itemId);
    public void decreaseQuantity(Long itemId);
    public void removeItem(Long itemId);
    public void checkout(List<CartItem> cartItems);

    public Optional<CartItem> findByProductInCurrentCart(Long productId);
    public void deleteCartItem(Long id);
}
