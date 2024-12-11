package com.example.server.config;

public class ConfigVendor {

    private int id;
    private boolean isStopped;
    private int releasedTickets;

    public ConfigVendor() {}

    public ConfigVendor(int id, boolean isStopped, int releasedTickets) {
        this.id = id;
        this.isStopped = isStopped;
        this.releasedTickets = releasedTickets;
    }

    public ConfigVendor(int id){
        this.id = id;
        this.isStopped = false;
        this.releasedTickets = 0;
    }

    public int getId() {
        return id;
    }

    public int getReleasedTickets() {
        return releasedTickets;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setReleasedTickets(int releasedTickets) {
        this.releasedTickets = releasedTickets;
    }

}
