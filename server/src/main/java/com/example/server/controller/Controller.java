package com.example.server.controller;

import com.example.server.Main;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/start")
    public String start() {
        String result = Main.start();
        return result;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/stop")
    public String stop() {
        Main.stop();
        return "Stopped";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/addVendor")
    public String addv() {
        String result = Main.addVendor();
        return result;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/addCustomer/{priority}")
    public String addc(@PathVariable("priority") int priority) {
        String result = Main.addCustomer(priority);
        return result;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/removeVendor/{id}")
    public String removeVendor(@PathVariable int id) {
        String result = Main.removeVendor(id);
        return result;
    }
}