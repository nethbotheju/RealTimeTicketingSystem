package com.example.server;

import com.example.server.config.LogConfig;
import com.example.server.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class Main {
    private static final ConcurrentLinkedQueue<Vendor> vendors = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Customer> customers = new ConcurrentLinkedQueue<>();
    public static boolean isProgramStopped = false;
    public static boolean isProgramStarted = false;

    private static int maxTicketCapacity = 100;
    private static int ticketsReleaseRate = 2;
    private static int customerRetrivalRate = 3;
    private static int totalNumberOfTickets = 0;

    private static TicketPool ticketPool = new TicketPool(maxTicketCapacity, totalNumberOfTickets);

    private static int numOfVendors = 4;
    private static int numOfCustomers = 4;

    private static int[] numOfCustomersPriority = new int[] {1, 2, 3, 1};

    private static int latestVendorId = numOfVendors + 1;
    private static int latestCustomerId = numOfCustomers + 1;

    private static final Logger logger = LogConfig.logger;

    public static void main(String[] args) throws ParseException {
    }

    public static String start() {
        if (!isProgramStarted) {
            for (int i = 1; i < numOfVendors + 1; i++) {
                Vendor vendor = new Vendor(i, ticketsReleaseRate, ticketPool);
                Thread thread = new Thread(vendor);
                vendors.add(vendor);
                thread.start(); // This will call the run() method.
            }

            for (int j = 1; j < numOfCustomers + 1; j++) {
                Customer customer = new Customer(j, customerRetrivalRate, ticketPool, numOfCustomersPriority[j - 1]);
                Thread thread = new Thread(customer);
                customers.add(customer);
                thread.start(); // This will call the run() method.
            }

            isProgramStarted = true;
            isProgramStopped = false;

            // Create an ObjectMapper to serialize the objects to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Create a map to hold both lists
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("vendors", vendors);
                resultMap.put("customers", customers);

                // Convert the map to JSON
                String jsonResult = objectMapper.writeValueAsString(resultMap);

                System.out.println("Start method started");
                return jsonResult; // Return the JSON string

            } catch (Exception e) {
                e.printStackTrace();
                return "{}"; // Return empty JSON in case of an error
            }
        }
        return null;
    }

    public static String removeVendor(int vendorId) {
        // in the frontend the remove buttons should be disabled
        if(!isProgramStopped){
            Iterator<Vendor> iterator = vendors.iterator();
            while (iterator.hasNext()) {
                Vendor vendor = iterator.next();
                if (vendor.getVendorId() == vendorId) {
                    vendor.setIsVendorStopped(true);
                    iterator.remove();
                    numOfVendors--;
                    logger.info("Vendor " + vendorId + " successfully removed from the vendors list.");
                    break;
                }
            }

            // Create an ObjectMapper to serialize the objects to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Create a map to hold both lists
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("vendors", vendors);

                // Convert the map to JSON
                String jsonResult = objectMapper.writeValueAsString(resultMap);

                return jsonResult; // Return the JSON string

            } catch (Exception e) {
                e.printStackTrace();
                return "{}"; // Return empty JSON in case of an error
            }
        }
        return null;
    }

    public static String removeCustomer(int customerId) {
        // in the frontend the remove buttons should be disabled
        if(!isProgramStopped){
            Iterator<Customer> iterator = customers.iterator();
            while (iterator.hasNext()) {
                Customer customer = iterator.next();
                if (customer.getCustomerId() == customerId) {
                    customer.setIsCustomerStopped(true);
                    iterator.remove();
                    numOfCustomers--;
                    logger.info("Customer " + customerId + " successfully removed from the customers list.");
                    break; // Exit the loop once the vendor is found and removed
                }
            }

            // Create an ObjectMapper to serialize the objects to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Create a map to hold both lists
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("customers", customers);

                // Convert the map to JSON
                String jsonResult = objectMapper.writeValueAsString(resultMap);

                return jsonResult; // Return the JSON string

            } catch (Exception e) {
                e.printStackTrace();
                return "{}"; // Return empty JSON in case of an error
            }
        }
        return null;
    }

    public static String addVendor() {
        // in the frontend the add buttons should be disabled
        if(!isProgramStopped){
            Vendor vendor = new Vendor(latestVendorId, ticketsReleaseRate, ticketPool);
            Thread thread = new Thread(vendor);
            vendors.add(vendor);
            numOfVendors++;
            logger.info("Vendor " + latestVendorId + " successfully added to the vendor list.");
            latestVendorId++;
            thread.start(); // This will call the run() method.

            // Create an ObjectMapper to serialize the objects to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Create a map to hold both lists
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("vendors", vendors);

                // Convert the map to JSON
                String jsonResult = objectMapper.writeValueAsString(resultMap);

                return jsonResult; // Return the JSON string

            } catch (Exception e) {
                e.printStackTrace();
                return "{}"; // Return empty JSON in case of an error
            }
        }
        return null;
    }

    public static String addCustomer(int priority) {
        // in the frontend the add buttons should be disabled
        if(!isProgramStopped){
            Customer customer = new Customer(latestCustomerId, customerRetrivalRate, ticketPool, priority);
            Thread thread = new Thread(customer);
            customers.add(customer);
            numOfCustomers++;
            logger.info("Customer " + latestCustomerId + " successfully added to the customer list.");
            latestCustomerId++;
            thread.start(); // This will call the run() method.

            // Create an ObjectMapper to serialize the objects to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Create a map to hold both lists
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("customers", customers);

                // Convert the map to JSON
                String jsonResult = objectMapper.writeValueAsString(resultMap);

                return jsonResult; // Return the JSON string

            } catch (Exception e) {
                e.printStackTrace();
                return "{}"; // Return empty JSON in case of an error
            }
        }
        return null;
    }

    public static void stop() {
        isProgramStopped = true;
        isProgramStarted = false;
        logger.info("Program stopped.");
        logger.info("Total number of selled tickets. " + ticketPool.getTotalNumberOfTickets());

        customers.clear();
        vendors.clear();

        System.out.println("Stop method started");
    }
}
