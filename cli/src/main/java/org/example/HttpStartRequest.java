package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStartRequest {
    public static void startHttp(){
        // Define the backend URL
        String url = "http://localhost:8080/api/start"; // Replace with your actual URL

        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and handle the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseBody -> {
                    // Process the response body
                    System.out.println("Response: " + responseBody);
                })
                .join(); // Wait for the async task to complete
    }

    public static void stopHttp(){
        // Define the backend URL
        String url = "http://localhost:8080/api/stop"; // Replace with your actual URL

        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the GET request
        HttpRequest request = HttpRequest.newBuilder()
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
}