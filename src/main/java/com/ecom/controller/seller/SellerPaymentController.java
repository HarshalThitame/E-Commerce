package com.ecom.controller.seller;

import com.ecom.customeexception.ResourceNotFoundException;
import com.ecom.entity.Payment;
import com.ecom.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller/payments")
public class SellerPaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/byOrderId/{id}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findByOrderID(id);
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id: " + id);
        }
        return ResponseEntity.ok(payment.get());
    }
}
