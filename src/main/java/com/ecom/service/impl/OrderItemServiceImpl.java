package com.ecom.service.impl;

import com.ecom.config.websocket.OrderWebSocketHandler;
import com.ecom.entity.OrderItem;
import com.ecom.entity.User;
import com.ecom.repositories.OrderItemRepository;
import com.ecom.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private static final Random random = new Random();

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderWebSocketHandler orderWebSocketHandler;

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {

        Long id = System.nanoTime();


        if (orderItem.getId() == null) {
            orderItem.setId(id);
        }
        OrderItem saved = orderItemRepository.save(orderItem);

        try {
            orderWebSocketHandler.notifyClients(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> findByOrderID(Long orderID) {
        return orderItemRepository.findByOrderId(orderID);
    }

    @Override
    public Optional<List<OrderItem>> getOrderItemsBySeller(User seller) {
        return orderItemRepository.findOrderItemsBySeller(seller);
    }

    @Override
    public List<OrderItem> findByProductId(Long id) {
        return orderItemRepository.findByProductId(id);
    }

    @Override
    public String notificationDemo(String str) {

        try {
            OrderItem orderItem = new OrderItem();
//            orderItem.getProduct().getSeller().setId(2L);
            orderWebSocketHandler.notifyClients(orderItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Hello";
    }
}
