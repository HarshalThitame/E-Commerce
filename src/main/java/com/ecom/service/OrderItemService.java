package com.ecom.service;

import com.ecom.entity.OrderItem;

import java.util.Optional;

public interface OrderItemService {
    Optional<OrderItem> findById(Long id);
    OrderItem save(OrderItem orderItem);
    void deleteById(Long id);
}

