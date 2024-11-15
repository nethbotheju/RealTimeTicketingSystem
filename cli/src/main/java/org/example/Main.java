package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the command: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("start")) {
            HttpStartRequest.startHttp();
        }
    }
}