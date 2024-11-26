package com.example.server.controller;

import com.example.server.model.Sale;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {
    private static SimpMessagingTemplate messagingTemplate;

    public SalesController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void sendToFrontendSale(Sale message) {
        messagingTemplate.convertAndSend("/topic/sales", message);
    }
}
