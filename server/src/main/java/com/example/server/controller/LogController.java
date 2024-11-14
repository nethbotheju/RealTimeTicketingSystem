package com.example.server.controller;

import com.example.server.model.LogEntry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
public class LogController {

    // Create a sink to hold and broadcast log entries
    private final static Sinks.Many<LogEntry> logSink = Sinks.many().replay().latest();

    // Endpoint to stream log entries as they are created
    @GetMapping(value = "/log-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LogEntry> streamLogs() {
        return logSink.asFlux().delayElements(Duration.ofMillis(500));  // Small delay to simulate real-time streaming
    }

    // Method to publish new log entries to the stream
    public static void publishLog(LogEntry logEntry) {
        logSink.tryEmitNext(logEntry);
    }
}

