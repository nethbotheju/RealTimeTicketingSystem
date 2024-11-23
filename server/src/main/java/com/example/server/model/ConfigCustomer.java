package com.example.server.model;

public class ConfigCustomer {
    private int id;
    private int priority;
    private boolean isStopped;
    private int retrivalTickets;

    public ConfigCustomer() {}

    public ConfigCustomer(int id, int priority, boolean isStopped, int retrivalTickets) {
        this.id = id;
        this.priority = priority;
        this.isStopped = isStopped;
        this.retrivalTickets = retrivalTickets;
    }

    public ConfigCustomer(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.isStopped = false;
        this.retrivalTickets = 0;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public int getRetrivalTickets() {
        return retrivalTickets;
    }
}
