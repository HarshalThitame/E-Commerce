package com.ecom.controller.seller;

import com.ecom.entity.Image;
import com.ecom.entity.OrderItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.service.ImageService;
import com.ecom.service.OrderItemService;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/seller/order-item")
public class SellerOrderItemController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductService productService;

    @GetMapping("/bySeller/{id}")
    public ResponseEntity<List<OrderItem>> getBySellerId(@PathVariable Long id) {
        User seller = new User();
        seller.setId(id);
        Optional<List<OrderItem>> orderItems = orderItemService.getOrderItemsBySeller(seller);

        for (OrderItem orderItem : orderItems.get()) {
            Product product = orderItem.getProduct();
            Optional<Product> newProduct = productService.findById(product.getId());
            if (newProduct.isPresent()) {
                orderItem.getProduct().setCategories(product.getCategories());
            }
            else{
                orderItem.getProduct().setCategories(new ArrayList<>());
            }
        }

        return orderItems.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byOrder/{id}")
    public ResponseEntity<OrderItem> getByOrderId(@PathVariable Long id) {
        return getOrderItemResponseEntity(id, orderItemService, imageService);
    }

    @GetMapping("/by-product/{id}")
    public ResponseEntity<List<OrderItem>> getByProductId(@PathVariable Long id) {

        List<OrderItem> orderItems = orderItemService.findByProductId(id);

        if (orderItems.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(orderItems);
    }

    public static ResponseEntity<OrderItem> getOrderItemResponseEntity(@PathVariable Long id, OrderItemService orderItemService, ImageService imageService) {
        Optional<OrderItem> orderItem = orderItemService.findById(id);
        Set<Image> images = imageService.getImagesByProductId(orderItem.get().getProduct().getId());
        orderItem.get().getProduct().setImages(images);
        return orderItem.map(item -> ResponseEntity.ok().body(item)).orElseGet(() -> ResponseEntity.ok(new OrderItem()));
    }
}
