package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the command: ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine(); // Read the user input once

        if (command.equals("start")) {
            HttpStartRequest.startHttp();
        } else if (command.equals("stop")) {
            HttpStartRequest.stopHttp();
        } else {
            System.out.println("Invalid command.");
        }
    }
}