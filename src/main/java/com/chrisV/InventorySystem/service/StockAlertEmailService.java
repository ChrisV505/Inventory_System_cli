package com.chrisV.InventorySystem.service;

import com.chrisV.InventorySystem.model.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface StockAlertEmailService {
    String sendSimpleEmail(EmailDetails details);
    String sendEmailWithAttachment(EmailDetails details);
}
