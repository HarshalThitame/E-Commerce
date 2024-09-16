package com.ecom.service;

import com.ecom.entity.OrderItem;
import com.ecom.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    Optional<OrderItem> findById(Long id);
    OrderItem save(OrderItem orderItem);
    void deleteById(Long id);

    List<OrderItem> findByOrderID(Long orderID);
    public Optional<List<OrderItem>> getOrderItemsBySeller(User seller);

    List<OrderItem> findByProductId(Long id);

    String notificationDemo(String str);

}

