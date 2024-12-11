package com.example.server.webSockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketAvailablilityController {
    private static SimpMessagingTemplate messagingTemplate;

    public TicketAvailablilityController(SimpMessagingTemplate messagingTemplate) {
        TicketAvailablilityController.messagingTemplate = messagingTemplate;
    }

    public static void sendToFrontendTicketAvail(int message) {
        messagingTemplate.convertAndSend("/topic/ticketAvail", message);
    }
}
