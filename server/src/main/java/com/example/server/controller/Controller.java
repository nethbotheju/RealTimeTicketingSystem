package com.example.server.controller;

import com.example.server.Main;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
public class Controller {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/start")
    public String start() {
        if(!Main.isProgramStarted) {
            String result = Main.start();
            return result;
        }
        return null;
    }
}
