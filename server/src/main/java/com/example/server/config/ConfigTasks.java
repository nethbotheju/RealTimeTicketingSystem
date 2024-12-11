package com.example.server.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigTasks {

    public static void updateConfig(String response) throws ParseException {
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(response);

        int maxTicketCapacity = ((Long) jsonObject.get("maxTicketCapacity")).intValue();
        int totalNumberOfTickets = ((Long) jsonObject.get("totalNumberOfTickets")).intValue();
        int ticketRetrivalRate = ((Long) jsonObject.get("ticketRetrivalRate")).intValue();
        int ticketReleaseRate = ((Long) jsonObject.get("ticketReleaseRate")).intValue();
        int numOfVendors = ((Long) jsonObject.get("numOfVendors")).intValue();
        int numOfCustomers = ((Long) jsonObject.get("numOfCustomers")).intValue();

        ConfigCustomer[] listOfCustomers;
        JSONArray Customers = (JSONArray) jsonObject.get("Customers");
        if(Customers != null){
            listOfCustomers = new ConfigCustomer[Customers.size()];
            for (int i = 0; i < Customers.size(); i++) {
                JSONObject customer = (JSONObject) Customers.get(i);
                int id = ((Long) customer.get("id")).intValue();
                int priority = ((Long) customer.get("priority")).intValue();
                listOfCustomers[i] = new ConfigCustomer(id, priority);
            }
        }else{
            listOfCustomers = new ConfigCustomer[numOfCustomers];
            for (int i = 0; i < numOfCustomers; i++) {
                listOfCustomers[i] = new ConfigCustomer(i+1, 3);
            }
        }

        ConfigVendor[] listOfVendors = new ConfigVendor[numOfVendors];
        for (int i = 0; i < numOfVendors; i++) {
            listOfVendors[i] = new ConfigVendor(i+1);
        }

        Configuration config = new Configuration(maxTicketCapacity, totalNumberOfTickets, ticketReleaseRate, ticketRetrivalRate, numOfVendors, numOfCustomers, listOfCustomers, listOfVendors);

        // Save the Config object to a JSON file
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create(); // For a nicely formatted JSON
            prettyGson.toJson(config, writer); // Serialize and write to file
        } catch (IOException e) {
            System.err.println("Error saving JSON: " + e.getMessage());
        }
    }

    public static Configuration loadConfigSystem() {
        Gson gson = new Gson();
        Configuration config;
        try (FileReader reader = new FileReader("config.json")) {
            config = gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    public static String loadConfigFrontend(){
        Gson gson = new Gson();
        Configuration config = loadConfigSystem();
        return gson.toJson(config);
    }

    public static void saveConfigSystem(Configuration config){
        // Save the Config object to a JSON file
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create(); // For a nicely formatted JSON
            prettyGson.toJson(config, writer); // Serialize and write to file
        } catch (IOException e) {
            System.err.println("Error saving JSON: " + e.getMessage());
        }
    }

    public static void resetConfigSystem(){
        Configuration config = loadConfigSystem();
        config.setTotalNumberOfTickets(0);
        for (ConfigCustomer configCustomer : config.getListOfCustomers()){
            configCustomer.setRetrivalTickets(0);
        }

        for (ConfigVendor configVendor : config.getListOfVendors()){
            configVendor.setReleasedTickets(0);
        }

        saveConfigSystem(config);
    }
}

