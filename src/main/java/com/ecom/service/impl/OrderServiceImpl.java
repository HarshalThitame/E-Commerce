package com.ecom.service.impl;

import com.ecom.config.websocket.OrderWebSocketHandler;
import com.ecom.entity.Order;
import com.ecom.entity.User;
import com.ecom.repositories.OrderRepository;
import com.ecom.repositories.UserRepository;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderWebSocketHandler orderWebSocketHandler;

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order save(Order order) {

        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findByUserId(Long id) {
        return orderRepository.findByUser(userRepository.findById(id).get());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> updateOrder(Order order) {
        return Optional.of(orderRepository.save(order));
    }


}
