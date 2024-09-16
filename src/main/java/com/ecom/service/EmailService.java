package com.ecom.service;

import com.ecom.entity.EmailData;

public interface EmailService {
    public String sendOtpEmail(String to);

    EmailData sendDatatoUser(String to, String subject, String body);
}
