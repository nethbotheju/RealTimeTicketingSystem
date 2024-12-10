package com.example.server.model;

import com.example.server.Main;
import com.example.server.config.DatabaseSetup;
import com.example.server.config.LogConfig;
import com.example.server.controller.Controller;
import com.example.server.controller.LogController;
import com.example.server.controller.SalesController;
import com.example.server.controller.TicketAvailablilityController;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.logging.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class TicketPool {
    public final int maxTicketCapacity;
    public  int totalNumberOfTickets;
    private int totalBoughTickets;

    public final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final PriorityBlockingQueue<Customer> waitingCustomers = new PriorityBlockingQueue<>(
            100,
            (c1, c2) -> Integer.compare(c1.getPriority(), c2.getPriority())
    );

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

                waitingCustomers.remove(customer);
                return null; // Exit the loop and method gracefully
            }

            // Check if the customer is manually stopped
            if (customer.getIsCustomerStopped()) {

                waitingCustomers.remove(customer);
                return null;
            }

            // Check if the maximum ticket capacity is reached and the pool is empty
            if (getTotalNumberOfTickets() >= maxTicketCapacity && isTicketPoolEmpty()) {

                waitingCustomers.remove(customer);
                return null;
            }

            // Check if the customer has the highest priority
            if (customer.equals(waitingCustomers.peek())) {
                lock.lock();
                try {
                    while (tickets.isEmpty()) {
                        notEmpty.await(); // Wait until tickets become available
                    }

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

                    waitingCustomers.poll();
                    return ticket;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    waitingCustomers.remove(customer);
                    return null;
                } finally {
                    lock.unlock();
                }
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