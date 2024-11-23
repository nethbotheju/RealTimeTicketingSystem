package com.example.server.model;

public class Configuration {
    private int maxTicketCapacity;
    private int totalNumberOfTickets;
    private int ticketRetrivalRate;
    private int ticketReleaseRate;
    private int numOfVendors;
    private int numOfCustomers;

    private ConfigVendor[] listOfVendors;
    private ConfigCustomer[] listOfCustomers;

    public Configuration() {}

    // Constructor with listOfVendors and listOfCustomers
    public Configuration(int maxTicketCapacity, int totalNumberOfTickets, int ticketReleaseRate, int ticketRetrivalRate, int numOfVendors, int numOfCustomers, ConfigCustomer[] listOfCustomers, ConfigVendor[] listOfVendors){
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalNumberOfTickets = totalNumberOfTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketRetrivalRate = ticketRetrivalRate;
        this.numOfVendors = numOfVendors;
        this.numOfCustomers = numOfCustomers;
        this.listOfCustomers = listOfCustomers;
        this.listOfVendors = listOfVendors;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalNumberOfTickets() {
        return totalNumberOfTickets;
    }

    public int getTicketRetrivalRate() {
        return ticketRetrivalRate;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getNumOfVendors() {
        return numOfVendors;
    }

    public int getNumOfCustomers() {
        return numOfCustomers;
    }

    public ConfigVendor[] getListOfVendors() {
        return listOfVendors;
    }

    public ConfigCustomer[] getListOfCustomers() {
        return listOfCustomers;
    }

    public void setTotalNumberOfTickets(int totalNumberOfTickets) {
        this.totalNumberOfTickets = totalNumberOfTickets;
    }
    public void setListOfVendors(ConfigVendor[] listOfVendors) {
        this.listOfVendors = listOfVendors;
    }

    public void setListOfCustomers(ConfigCustomer[] listOfCustomers) {
        this.listOfCustomers = listOfCustomers;
    }

    public void setNumOfVendors(int numOfVendors) {
        this.numOfVendors = numOfVendors;
    }

    public void setNumOfCustomers(int numOfCustomers) {
        this.numOfCustomers = numOfCustomers;
    }
}

