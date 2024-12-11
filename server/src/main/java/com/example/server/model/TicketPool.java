package com.example.server.model;

import com.example.server.Main;
import com.example.server.cli.ServerSocketCLI;
import com.example.server.database.DatabaseSetup;
import com.example.server.logging.LogConfig;
import com.example.server.webSockets.LogController;
import com.example.server.webSockets.SalesController;
import com.example.server.webSockets.TicketAvailablilityController;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.logging.Logger;

import java.time.LocalDateTime;


public class TicketPool {
    public final int maxTicketCapacity;
    public  int totalNumberOfTickets;
    private int totalBoughTickets;

    public final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final PriorityBlockingQueue<Customer> waitingCustomers = new PriorityBlockingQueue<>(
            100,
            (c1, c2) -> Integer.compare(c1.getPriority(), c2.getPriority())
    );

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    private static final Logger logger = LogConfig.logger;

    public TicketPool(int maxTicketCapacity, int totalNumberOfTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalNumberOfTickets = totalNumberOfTickets;
        totalBoughTickets = totalNumberOfTickets;
    }

    public void addTickets(Ticket ticket) {
        lock.lock();
        try {
            tickets.add(ticket);
            totalNumberOfTickets++;


            String message = "Vendor " + ticket.getVendorId() + " successfully added a ticket to the TicketPool.";
            logger.info(message);
            LogController.sendToFrontendLog(new LogEntry("Success", message, LocalDateTime.now().format(formatter)));
            ServerSocketCLI.sendMessage(message);


            notEmpty.signalAll(); // Signal that tickets are available
        } finally {
            lock.unlock();
        }
    }

    public Ticket removeTicket(Customer customer) {
        waitingCustomers.put(customer);
        while (true) {
            // Check if the program is stopped
            if (Main.isProgramStopped) {
                String message = "Program stopped. So customer " + customer.getCustomerId() + " stopped.";
                logger.info(message);
                LogController.sendToFrontendLog(new LogEntry("Success", message, LocalDateTime.now().format(formatter)));

                return null;
            }

            if (customer.getIsCustomerStopped()) {
                String message = "Customer " + customer.getCustomerId() + " stopped successfully.";
                logger.info(message);
                LogController.sendToFrontendLog(new LogEntry("Success", message, LocalDateTime.now().format(formatter)));

                return null;
            }


            lock.lock();
            try {
                if(tickets.isEmpty() && totalNumberOfTickets >= maxTicketCapacity) {
                    waitingCustomers.remove(customer);

                    String message = "Total number of tickets released has reached the limit for Vendor and the ticket pool is empty, so customer " + customer.getCustomerId() + ". Stopping buying tickets.";
                    logger.info(message);
                    LogController.sendToFrontendLog(new LogEntry("Warning", message, LocalDateTime.now().format(formatter)));
                    ServerSocketCLI.sendMessage(message);

                    return null;
                }

                if(tickets.isEmpty()) {
                    notEmpty.await(); // Wait until tickets become available
                }

                if (!tickets.isEmpty() && customer.equals(waitingCustomers.peek())) {
                    // Remove a ticket and perform necessary actions
                    Ticket ticket = tickets.remove(0);
                    totalBoughTickets++;

                    String message = "Customer " + customer.getCustomerId() + " successfully removed a ticket from the TicketPool.";
                    logger.info(message);
                    LocalDateTime dateTime = LocalDateTime.now();
                    LogController.sendToFrontendLog(new LogEntry("Success", message, dateTime.format(formatter)));
                    SalesController.sendToFrontendSale(new Sale(dateTime.format(dateFormat), 1));
                    DatabaseSetup.insertIntoSales(dateTime.format(dateFormat), 1);
                    TicketAvailablilityController.sendToFrontendTicketAvail(totalNumberOfTickets - totalBoughTickets);
                    ServerSocketCLI.sendMessage(message);

                    waitingCustomers.poll();
                    return ticket;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                waitingCustomers.remove(customer);
                return null;
            } finally {
                lock.unlock();
            }

        }
    }


    public int getTotalNumberOfTickets() {
        lock.lock();
        try{
            return totalNumberOfTickets;
        }finally {
            lock.unlock();
        }
    }

    public boolean isTicketPoolEmpty() {
            return tickets.isEmpty();
    }

    public int getTotalBoughTickets() {
        return totalBoughTickets;
    }
}