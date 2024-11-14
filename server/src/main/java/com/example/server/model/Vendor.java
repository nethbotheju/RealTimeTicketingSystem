package com.example.server.model;

import com.example.server.Main;
import com.example.server.config.LogConfig;
import com.example.server.controller.LogController;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketsReleaseRate;
    private final int vendorId;
    private boolean isVendorStopped;

    private static final Logger logger = LogConfig.logger;
    private final ReentrantLock lock = new ReentrantLock();

    public Vendor(int vendorId, int ticketsReleaseRate, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsReleaseRate = ticketsReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        int releaseTicketCount = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();
                try {
                    if (Main.isProgramStopped) {
                        String message = "Program stopped. So vendor " + vendorId + " stopped.";
                        logger.info(message);
                        LogController.publishLog(new LogEntry("Success", message, LocalDateTime.now().toString()));

                        Thread.currentThread().interrupt();
                        break;
                    }

                    if (isVendorStopped) {
                        String message = "Vendor " + vendorId + " stopped successfully.";
                        logger.info(message);
                        LogController.publishLog(new LogEntry("Success", message, LocalDateTime.now().toString()));

                        Thread.currentThread().interrupt();
                        break;
                    }

                    if (ticketPool.getTotalNumberOfTickets() >= ticketPool.maxTicketCapacity) {
                        String message = "Total number of tickets released has reached the limit for Vendor " + vendorId + ". Stopping further releases.";
                        logger.info(message);
                        LogController.publishLog(new LogEntry("Warning", message, LocalDateTime.now().toString()));

                        Thread.currentThread().interrupt();
                        break;
                    }

                    ticketPool.addTickets(new Ticket(vendorId));
                    releaseTicketCount++;

                } finally {
                    lock.unlock();
                }

                Thread.sleep(1000 / ticketsReleaseRate); // ticket release rate per second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.severe("Error: " + e.getMessage());
                break;
            }
        }
    }

    public void setIsVendorStopped(boolean result) {
        lock.lock();
        try {
            isVendorStopped = result;
        } finally {
            lock.unlock();
        }
    }

    public int getVendorId() {
        return vendorId;
    }
}
