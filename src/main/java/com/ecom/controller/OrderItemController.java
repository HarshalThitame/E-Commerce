package com.ecom.controller;

import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import com.ecom.service.ImageService;
import com.ecom.service.OrderItemService;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.controller.seller.SellerOrderItemController.getOrderItemResponseEntity;

@RestController
@RequestMapping("/api/user/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ImageService imageService;




    @GetMapping("/notification")
    public ResponseEntity<?> sendnotification() {

        String str = "Hello";

        String s = orderItemService.notificationDemo(str);
//        messagingTemplate.convertAndSend("/topic/orders", str);

        return ResponseEntity.ok().body(s);

    }

    @PostMapping
    public ResponseEntity<OrderItem> saveOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem item = orderItemService.save(orderItem);

        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/byUser/{userID}")
    public ResponseEntity<List<OrderItem>> getOrderItemByUser(@PathVariable Long userID) {
        List<Order> orders = orderService.findByUserId(userID);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> oi = orderItemService.findByOrderID(order.getId());
            orderItems.addAll(oi);
        }

        if (!orderItems.isEmpty()) {
            return ResponseEntity.ok().body(orderItems);
        }

        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/byOrder/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        return getOrderItemResponseEntity(id, orderItemService, imageService);
    }



}
