package com.example.server.webSockets;

import com.example.server.model.LogEntry;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static SimpMessagingTemplate messagingTemplate;

    public LogController(SimpMessagingTemplate messagingTemplate) {
        LogController.messagingTemplate = messagingTemplate;
    }

    public static void sendToFrontendLog(LogEntry message) {
        messagingTemplate.convertAndSend("/topic/log", message);
    }
}

