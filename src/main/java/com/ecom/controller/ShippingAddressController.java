package com.ecom.controller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.ShippingAddress;
import com.ecom.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/shipping-address")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        Optional<ShippingAddress> shippingAddress = shippingAddressService.findById(id);
        if (shippingAddress.isEmpty()) {
            throw new ResourceNotFoundException("ShippingAddress not found with id: " + id);
        }
        return ResponseEntity.ok(shippingAddress.get());
    }

    @GetMapping("/byOrder/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressByOrderId(@PathVariable Long id) {
        Optional<ShippingAddress> shippingAddress = shippingAddressService.getShippingAddressByOrderId(id);
        if (shippingAddress.isEmpty()) {
            throw new ResourceNotFoundException("ShippingAddress not found with id: " + id);
        }
        return ResponseEntity.ok(shippingAddress.get());
    }

    @PostMapping
    public ResponseEntity<ShippingAddress> createShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        ShippingAddress newShippingAddress = shippingAddressService.save(shippingAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShippingAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddressById(@PathVariable Long id) {
        shippingAddressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

