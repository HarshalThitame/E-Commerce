package com.ecom.service.impl;

import com.ecom.entity.Cart;
import com.ecom.entity.User;
import com.ecom.repositories.CartRepository;
import com.ecom.service.CartService;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCurrentCart() {
        User currentUser = userService.getCurrentUser(); // Implement logic to get current user
        return cartRepository.findByUser(currentUser);
    }
}
