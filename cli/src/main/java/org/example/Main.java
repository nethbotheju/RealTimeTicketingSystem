package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class Main {
    private static volatile boolean started = false;
    private static volatile boolean stopped = true;
    private static Scanner scanner = new Scanner(System.in);
    private static Socket socket;

    public static void main(String[] args) {
        // Method for getting messages from the backend and saving

        System.out.println("First input the Configuration Parameters");

        int maxTicketCapacity = 0;
        int totalTicketNumber = 0;
        int customerRetrievalRate = 0;
        int ticketReleaseRate = 0;
        int noOfVendors = 0;
        int noOfCustomers = 0;

        while (true) {
            System.out.print("\nMax Ticket Capacity: ");
            if (scanner.hasNextInt()) {
                maxTicketCapacity = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Max Ticket Capacity");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("\nTotal Ticket Number: ");
            if (scanner.hasNextInt()) {
                totalTicketNumber = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Total Ticket Number");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("\nCustomer retrieval rate (per 10 second): ");
            if (scanner.hasNextInt()) {
                customerRetrievalRate = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Customer retrieval rate");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("\nTicket Release rate (per 10 second): ");
            if (scanner.hasNextInt()) {
                ticketReleaseRate = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Ticket Release rate");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("\nNumber Of vendors: ");
            if (scanner.hasNextInt()) {
                noOfVendors = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Number Of vendors");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("\nNumber Of customers: ");
            if (scanner.hasNextInt()) {
                noOfCustomers = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an integer value for Number Of customers");
                scanner.next();
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("maxTicketCapacity", maxTicketCapacity);
        jsonObject.put("totalNumberOfTickets", totalTicketNumber);
        jsonObject.put("ticketRetrivalRate", customerRetrievalRate);
        jsonObject.put("ticketReleaseRate", ticketReleaseRate);
        jsonObject.put("numOfVendors", noOfVendors);
        jsonObject.put("numOfCustomers", noOfCustomers);

        HttpRequest.configHttp(jsonObject.toString());


        try {
            socket = new Socket("localhost", 1234);
            System.out.println("Connected to Backend server");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String msg;
                        while ((msg = input.readLine()) != null) {
                            if (msg.equals("started")) {
                                started = true;
                                stopped = false;
                            } else if (msg.equals("stopped")) {
                                started = false;
                                stopped = true;
                            } else {
                                System.out.println("Backend: " + msg);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error : " + e.getMessage());
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("\nEnter the command: ");
                    String command = scanner.next(); // Read the user input once

                    if (command.equals("start")) {
                        if (!started) {
                            HttpRequest.startHttp();
                        } else {
                            System.out.println("Program already started");
                        }
                    } else if (command.equals("stop")) {
                        if (!stopped) {
                            HttpRequest.stopHttp();
                        } else {
                            System.out.println("Program already stopped");
                        }
                    } else if (command.equals("q")) {
                        System.out.println("CLI Client is stopping .....");
                        try {
                            if (socket != null && !socket.isClosed()) {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        scanner.close();
                        System.exit(0);
                    } else {
                        System.out.println("Invalid command.");
                    }
                }
            }
        }).start();
    }
}