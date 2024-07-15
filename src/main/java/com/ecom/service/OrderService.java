package com.ecom.service;

import com.ecom.entity.Order;
import com.ecom.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(Long id);
    List<Order> findByUser(User user);
    Order save(Order order);
    void deleteById(Long id);
    List<Order> findByUserId(Long id);
}
