package com.ecom.service;

import com.ecom.entity.ShippingAddress;

import java.util.List;
import java.util.Optional;

public interface ShippingAddressService {
    Optional<ShippingAddress> findById(Long id);
    ShippingAddress save(ShippingAddress shippingAddress);
    void deleteById(Long id);

    List<ShippingAddress> getAllShippingAddresses();

    Optional<ShippingAddress> getShippingAddressByOrderId(Long id);
}

