package com.example.server;

import com.example.server.config.LogConfig;
import com.example.server.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class Main {
    private static ConcurrentLinkedQueue<Vendor> vendors = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Customer> customers = new ConcurrentLinkedQueue<>();
    public static boolean isProgramStopped = false;
    public static boolean isProgramStarted = false;

    private static int maxTicketCapacity;
    private static int ticketsReleaseRate;
    private static int customerRetrivalRate;
    private static int totalNumberOfTickets;

    private static TicketPool ticketPool;

    private static int numOfVendors;
    private static int numOfCustomers;

    private static final Logger logger = LogConfig.logger;

    private static Configuration config;

    private static ConfigVendor[] listOfVendors;
    private static ConfigCustomer[] listOfCustomers;

    public static void main(String[] args) throws ParseException {
    }

    public static String start() throws FileNotFoundException {

        config = ConfigTasks.loadConfigSystem();

        maxTicketCapacity = config.getMaxTicketCapacity();
        totalNumberOfTickets = config.getTotalNumberOfTickets();
        customerRetrivalRate = config.getTicketRetrivalRate();
        ticketsReleaseRate = config.getTicketReleaseRate();
        numOfCustomers = config.getNumOfCustomers();
        numOfVendors = config.getNumOfVendors();

        listOfVendors = config.getListOfVendors();
        listOfCustomers = config.getListOfCustomers();

        ticketPool = new TicketPool(maxTicketCapacity, totalNumberOfTickets);

        if (!isProgramStarted) {
            for (int i = 0; i < numOfVendors; i++) {
                Vendor vendor = new Vendor(listOfVendors[i].getId(), ticketsReleaseRate, ticketPool, listOfVendors[i].isStopped(), listOfVendors[i].getReleasedTickets());
                vendors.add(vendor);
                if(!listOfVendors[i].isStopped()) {
                    Thread thread = new Thread(vendor);
                    thread.start(); // This will call the run() method.
                }
            }

            for (int j = 0; j < numOfCustomers; j++) {
                Customer customer = new Customer(listOfCustomers[j].getId(), customerRetrivalRate, ticketPool, listOfCustomers[j].getPriority(), listOfCustomers[j].isStopped(), listOfCustomers[j].getRetrivalTickets() );
                customers.add(customer);
                if(!listOfCustomers[j].isStopped()) {
                    Thread thread = new Thread(customer);
                    thread.start(); // This will call the run() method.
                }
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

                System.out.println("Start method started with result: " + jsonResult);
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
            for(Vendor v : vendors){
                if (v.getVendorId() == vendorId) {
                    v.setIsVendorStopped(true);

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
            for(Customer c : customers){
                if (c.getCustomerId() == customerId) {
                    c.setIsCustomerStopped(true);

                    logger.info("Customer " + customerId + " successfully stoped.");
                    break;
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
            int newVendorId = numOfVendors + 1;
            Vendor vendor = new Vendor(newVendorId, ticketsReleaseRate, ticketPool, false, 0);
            Thread thread = new Thread(vendor);
            vendors.add(vendor);

            logger.info("Vendor " + newVendorId + " successfully added to the vendor list.");
            numOfVendors = newVendorId;
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
            int newCustomerId = numOfCustomers + 1;
            Customer customer = new Customer(newCustomerId, customerRetrivalRate, ticketPool, priority, false, 0);
            Thread thread = new Thread(customer);
            customers.add(customer);

            logger.info("Customer " + newCustomerId + " successfully added to the customer list.");
            numOfCustomers = newCustomerId;
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

    public static void stop() throws FileNotFoundException {
        isProgramStopped = true;
        isProgramStarted = false;
        logger.info("Program stopped.");
        logger.info("Total number of selled tickets. " + ticketPool.getTotalNumberOfTickets());

        // Update config with total tickets sold
        config.setTotalNumberOfTickets(ticketPool.getTotalNumberOfTickets());

        // Create new customer list
        ConfigCustomer[] newCustomerList = new ConfigCustomer[customers.size()];
        int customerIndex = 0;
        for (Customer c : customers) { // Correct iteration for customers
            ConfigCustomer newCust = new ConfigCustomer(c.getCustomerId(), c.getPriority(), c.getIsCustomerStopped(), c.getBoughtTickets());
            newCustomerList[customerIndex++] = newCust;
        }

        // Create new vendor list
        ConfigVendor[] newVendorList = new ConfigVendor[vendors.size()];
        int vendorIndex = 0;
        for (Vendor v : vendors) { // Correct iteration for vendors
            ConfigVendor newVend = new ConfigVendor(v.getVendorId(), v.getIsVendorStopped(), v.getReleaseTicketCount());
            newVendorList[vendorIndex++] = newVend;
        }

        config.setNumOfCustomers(numOfCustomers);
        config.setNumOfVendors(numOfVendors);

        config.setListOfCustomers(newCustomerList);
        config.setListOfVendors(newVendorList);

        ConfigTasks.saveConfigSystem(config);

        customers.clear();
        vendors.clear();

        System.out.println("Stop method started");
    }
}
