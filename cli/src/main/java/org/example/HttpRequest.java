package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class HttpRequest {
    public static void startHttp(){
        // Define the backend URL
        String url = "http://localhost:8080/api/start"; // Replace with your actual URL

        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the GET request
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and handle the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    // Process the response body
                    System.out.println("Response: " + responseBody);
                }).exceptionally(e -> {
                    System.err.println("Request failed: " + e.getMessage());
                    return null;
                })
                .join(); // Wait for the async task to complete
    }

    public static void stopHttp(){
        // Define the backend URL
        String url = "http://localhost:8080/api/stop"; // Replace with your actual URL

        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the GET request
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and handle the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    // Process the response body
                    System.out.println("Response: " + responseBody);
                }).exceptionally(e -> {
                    System.err.println("Request failed: " + e.getMessage());
                    return null;
                })
                .join(); // Wait for the async task to complete
    }

    public static void configHttp(String requestBody) {
        // Define the backend URL
        String url = "http://localhost:8080/api/updateConfig/"; // Replace with your actual URL

        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the POST request with the request body
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json") // Set the content type if needed
                .build();

        // Send the request and handle the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    System.out.println("Response: " + responseBody);
                }).exceptionally(e -> {
                    if(e.getCause() instanceof java.net.ConnectException){
                        System.out.println("\n Unable to connect to server. Make sure server is running.");
                    }else{
                        System.err.println("HTTP Request failed: " + e.getMessage());
                    }

                    return null;
                })
                .join(); // Wait for the async task to complete
    }
}