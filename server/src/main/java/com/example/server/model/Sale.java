package com.example.server.model;

public class Sale {
    private String date;
    private int count;

    public Sale(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }
}
