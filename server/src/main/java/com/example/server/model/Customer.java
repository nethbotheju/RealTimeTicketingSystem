package com.example.server.model;

import com.example.server.Main;
import com.example.server.config.LogConfig;
import com.example.server.controller.LogController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrivalRate;
    private final int customerId;
    private boolean isCustomerStopped;
    private final int priority;

    private int boughtTickets;


    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");

    private static final Logger logger = LogConfig.logger;
    private final ReentrantLock lock = new ReentrantLock();

    public Customer(int customerId, int customerRetrivalRate, TicketPool ticketPool, int priority, boolean isCustomerStopped, int boughtTickets) {
        this.customerId = customerId;
        this.customerRetrivalRate = customerRetrivalRate;
        this.ticketPool = ticketPool;
        this.priority = priority;
        this.isCustomerStopped = isCustomerStopped;
        this.boughtTickets = boughtTickets;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                    Ticket ticket = ticketPool.removeTicket(this);
                    if (ticket != null) {
                        boughtTickets++;
                    }else{
                        Thread.currentThread().interrupt();
                    }


                Thread.sleep(10000 / customerRetrivalRate); // customer ticket retrieval rate per second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.severe("Error: " + e.getMessage());
                break;
            }
        }
    }

    public void setIsCustomerStopped(boolean result) {
            isCustomerStopped = result;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getIsCustomerStopped() {
        return isCustomerStopped;
    }

    public int getBoughtTickets() {
        return boughtTickets;
    }
}