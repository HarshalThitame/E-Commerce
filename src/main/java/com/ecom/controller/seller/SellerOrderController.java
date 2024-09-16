package com.ecom.controller.seller;

import com.ecom.entity.Order;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ecom.controller.OrderController.getOrderResponseEntity;

@RestController
@RequestMapping("/api/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/byOrder")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        return getOrderResponseEntity(order, orderService);
    }
}
