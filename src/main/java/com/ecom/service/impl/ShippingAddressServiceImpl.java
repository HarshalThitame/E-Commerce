package com.ecom.service.impl;

import com.ecom.entity.ShippingAddress;
import com.ecom.repositories.ShippingAddressRepository;
import com.ecom.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Override
    public Optional<ShippingAddress> findById(Long id) {
        return shippingAddressRepository.findById(id);
    }

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        long id = System.currentTimeMillis();
        shippingAddress.setId(id);
        return shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void deleteById(Long id) {
        shippingAddressRepository.deleteById(id);
    }

    @Override
    public List<ShippingAddress> getAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }

    @Override
    public Optional<ShippingAddress> getShippingAddressByOrderId(Long id) {
        return shippingAddressRepository.findByOrderId(id);
    }
}
