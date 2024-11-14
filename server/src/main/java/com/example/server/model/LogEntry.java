package com.example.server.model;

public class LogEntry {
    private String level;      // e.g., "INFO", "ERROR", "SUCCESS"
    private String message;    // Log message description
    private String timestamp;  // Timestamp of the log event

    // Constructor
    public LogEntry(String level, String message, String timestamp) {
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
