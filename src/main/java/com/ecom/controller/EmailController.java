package com.ecom.controller;

import com.ecom.entity.EmailData;
import com.ecom.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @PostMapping
    public ResponseEntity<?> sendOrderDetailsTouser(@RequestBody EmailData emailData ) {

        EmailData emailData1 = emailService.sendDatatoUser(emailData.getTo(), emailData.getSubject(), emailData.getBody());

        return ResponseEntity.ok().body(emailData1);
    }
}
