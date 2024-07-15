package com.ecom.service;

import com.ecom.entity.Payment;

import java.util.Optional;

public interface PaymentService {
    Optional<Payment> findById(Long id);
    Payment save(Payment payment);
    void deleteById(Long id);
}

