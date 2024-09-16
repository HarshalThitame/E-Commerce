package com.ecom.controller;

import com.ecom.entity.Order;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    public static ResponseEntity<Order> getOrderResponseEntity(@RequestBody Order order, OrderService orderService) {
        Optional<Order> orderId = orderService.findById(order.getId());

        if (orderId.isPresent()) {
            Optional<Order> updatedOrder = orderService.updateOrder(order);
            return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new Order()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {

        Long id = System.nanoTime();
        Long id2 = System.currentTimeMillis();
        order.setId(id+id2);
        Order newOrder = orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
    @PutMapping("/byOrder")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        return getOrderResponseEntity(order, orderService);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

