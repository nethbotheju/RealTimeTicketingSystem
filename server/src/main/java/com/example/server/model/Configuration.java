package com.example.server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.FileReader;
import java.io.IOException;

public class Configuration {

    private static int numOfVendors;
    private static int numOfCustomers;
    private static int maxTicketCapacity;
    private static int ticketsReleaseRate;
    private static int customerRetrivalRate;
    private static int totalNumberOfTickets;
    private static int[] numOfCustomersPriority;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void readJsonFile() throws IOException {
        try (FileReader reader = new FileReader("data.json")) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            numOfVendors = jsonObject.get("numOfVendors").getAsInt();
            numOfCustomers = jsonObject.get("numOfCustomers").getAsInt();
            maxTicketCapacity = jsonObject.get("maxTicketCapacity").getAsInt();
            ticketsReleaseRate = jsonObject.get("ticketsReleaseRate").getAsInt();
            customerRetrivalRate = jsonObject.get("customerRetrivalRate").getAsInt();
            totalNumberOfTickets = jsonObject.get("totalNumberOfTickets").getAsInt();

            JsonArray jsonArray = jsonObject.getAsJsonArray("numOfCustomersPriority");
            numOfCustomersPriority = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                numOfCustomersPriority[i] = Integer.parseInt(jsonArray.get(i).getAsString()); // first get the number as string and then convert it to int. but if you pass int values to the json not need to convert
            }
        }
    }

    // Getters for the static variables
    public static int getNumOfVendors() {
        return numOfVendors;
    }

    public static int getNumOfCustomers() {
        return numOfCustomers;
    }

    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public static int getTicketsReleaseRate() {
        return ticketsReleaseRate;
    }

    public static int getCustomerRetrivalRate() {
        return customerRetrivalRate;
    }

    public static int getTotalNumberOfTickets() {
        return totalNumberOfTickets;
    }

    public static int[] getNumOfCustomersPriority() {
        return numOfCustomersPriority;
    }
}

