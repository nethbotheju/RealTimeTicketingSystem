package com.example.server.model;

public class Ticket {
    private int vendorId;
    private int customerId;

    public Ticket(int vendorId){
        this.vendorId = vendorId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public int getCustomerId(){
        return customerId;
    }

    public int getVendorId(){
        return vendorId;
    }
}
