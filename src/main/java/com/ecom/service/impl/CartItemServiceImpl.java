package com.ecom.service.impl;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.Cart;
import com.ecom.entity.CartItem;
import com.ecom.entity.Product;
import com.ecom.repositories.CartItemRepository;
import com.ecom.service.CartItemService;
import com.ecom.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartService cartService;

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findCartItemsByUserId(userId);
    }

    @Override
    public CartItem increaseQuantity(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        item.setQuantity(item.getQuantity() + 1);
        cartItemRepository.save(item);
        return item;
    }

    @Override
    public void decreaseQuantity(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        }
    }

    @Override
    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public void checkout(List<CartItem> cartItems) {
        // Handle checkout logic
        // This might include updating the order status, reducing stock quantities, etc.
    }

    @Override
    public Optional<CartItem> findByProductInCurrentCart(Long productId) {
        Cart cart = cartService.getCurrentCart(); // Implement logic to get the current cart
        Product product = new Product();
        product.setId(productId);
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Transactional
    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteByCartId(id);
    }
}

