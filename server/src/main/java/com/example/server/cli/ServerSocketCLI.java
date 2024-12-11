package com.example.server.cli;

import com.example.server.logging.LogConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketCLI {
    private static ServerSocket serverSocket;
    private static OutputStream output;
    private static Socket socket;

    public static void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    serverSocket = new ServerSocket(1234);
                    socket = serverSocket.accept();
                    output = socket.getOutputStream();
                    System.out.println("CLI Client is connected to the backend server.");
                    LogConfig.logger.info("CLI Client is connected to the backend server.");
                } catch (IOException e) {
                    System.out.println("ServerSocketClI error: " + e.getMessage());
                }
            }
        }).start();
    }

    public static synchronized void sendMessage(String msg){
        if(socket != null){
            try{
                output.write((msg + "\n").getBytes());
            } catch (IOException e) {
                System.out.println("ServerSocketClI error: " + e.getMessage());
            }
        }
    }
}
