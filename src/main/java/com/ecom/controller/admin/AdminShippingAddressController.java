package com.ecom.controller.admin;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.ShippingAddress;
import com.ecom.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/shipping-addresses")
public class AdminShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses() {
        List<ShippingAddress> addresses = shippingAddressService.getAllShippingAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        Optional<ShippingAddress> address = shippingAddressService.findById(id);
        if (!address.isPresent()) {
            throw new ResourceNotFoundException("ShippingAddress not found with id: " + id);
        }
        return ResponseEntity.ok(address.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddressById(@PathVariable Long id) {
        shippingAddressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

