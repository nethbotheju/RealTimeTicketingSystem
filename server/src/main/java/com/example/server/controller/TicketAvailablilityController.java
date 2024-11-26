package com.example.server.controller;

import com.example.server.model.Sale;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketAvailablilityController {
    private static SimpMessagingTemplate messagingTemplate;

    public TicketAvailablilityController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void sendToFrontendTicketAvail(int message) {
        messagingTemplate.convertAndSend("/topic/ticketAvail", message);
    }
}
