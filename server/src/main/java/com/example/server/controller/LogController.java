package com.example.server.controller;

import com.example.server.model.LogEntry;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
public class LogController {

    private static SimpMessagingTemplate messagingTemplate;

    public LogController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void sendToFrontendLog(LogEntry message) {
        messagingTemplate.convertAndSend("/topic/log", message);
    }
}

