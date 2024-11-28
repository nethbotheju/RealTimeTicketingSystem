package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static volatile boolean started = false;
    private static volatile  boolean stopped = true;

    public static void main(String[] args) {
        // Method for the getting messages from the backend and save
        try{
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to Backend server");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String msg;
                        while ((msg = input.readLine()) != null){
                            if(msg.equals("started")){
                                started = true;
                                stopped = false;
                            }else if(msg.equals("stopped")){
                                started = false;
                                stopped = true;
                            }else{
                                System.out.println("Backend: " + msg);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }catch (IOException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println("Enter the command: ");
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine(); // Read the user input once

                    if (command.equals("start")) {
                        if(!started){
                            HttpStartRequest.startHttp();
                        }else{
                            System.out.println("Program already started");
                        }
                    } else if (command.equals("stop")) {
                        if(!stopped){
                            HttpStartRequest.stopHttp();
                        }else{
                            System.out.println("Program already stopped");
                        }
                    } else {
                        System.out.println("Invalid command.");
                    }
                }
            }
        }).start();
    }
}