package com.example.server.model;

import com.example.server.config.LogConfig;
import com.example.server.config.SQLiteDatabaseSetup;
import com.example.server.controller.LogController;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.logging.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class TicketPool {
    public final int maxTicketCapacity;
    public  int totalNumberOfTickets;
    public final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final List<Customer> waitingCustomers = Collections.synchronizedList(new ArrayList<>());

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private static final Logger logger = LogConfig.logger;

    public TicketPool(int maxTicketCapacity, int totalNumberOfTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalNumberOfTickets = totalNumberOfTickets;
    }

    public void addTickets(Ticket ticket) {
        lock.lock();
        try {
            while (tickets.size() >= maxTicketCapacity) {
                notFull.await(); // Wait if ticket pool is full
            }
            tickets.add(ticket);
            totalNumberOfTickets++;


            String message = "Vendor " + ticket.getVendorId() + " successfully added a ticket to the TicketPool.";
            logger.info(message);
            LogController.sendToFrontendLog(new LogEntry("Success", message, LocalDateTime.now().format(formatter)));


            notEmpty.signalAll(); // Signal that tickets are available
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public Ticket removeTicket(Customer customer) {
        lock.lock();
        try {
            waitingCustomers.add(customer);
            while (tickets.isEmpty() || !customer.equals(getHighestPriorityCustomer())) {
                notEmpty.await(); // Wait until there are tickets or the customer has priority
            }
            Ticket ticket = tickets.remove(0);

            String message = "Customer " + customer.getCustomerId() + " successfully removed a ticket from the TicketPool.";
            logger.info(message); // logging file
            LogController.sendToFrontendLog(new LogEntry("Success", message, LocalDateTime.now().format(formatter))); // real time send to frontend

            waitingCustomers.remove(customer);
            notFull.signalAll(); // Signal that there is space for more tickets

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    private Customer getHighestPriorityCustomer() {
        lock.lock();
        try {
            int highestPriority = waitingCustomers.stream()
                    .mapToInt(Customer::getPriority)
                    .min()
                    .orElse(Integer.MAX_VALUE);

            // Return the first customer with the highest priority
            return waitingCustomers.stream()
                    .filter(customer -> customer.getPriority() == highestPriority)
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.unlock();
        }
    }

    public int getTotalNumberOfTickets() {
        lock.lock();
        try {
            return totalNumberOfTickets;
        } finally {
            lock.unlock();
        }
    }

    public boolean isTicketPoolEmpty() {
        lock.lock();
        try {
            return tickets.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}
