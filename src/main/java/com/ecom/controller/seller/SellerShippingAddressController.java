package com.ecom.controller.seller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.ShippingAddress;
import com.ecom.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller/shipping-address")
public class SellerShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping("/byOrder/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressByOrderId(@PathVariable Long id) {
        Optional<ShippingAddress> shippingAddress = shippingAddressService.getShippingAddressByOrderId(id);
        if (shippingAddress.isEmpty()) {
            throw new ResourceNotFoundException("ShippingAddress not found with id: " + id);
        }
        return ResponseEntity.ok(shippingAddress.get());
    }
}
