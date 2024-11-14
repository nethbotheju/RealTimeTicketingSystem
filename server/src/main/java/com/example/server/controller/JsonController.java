package com.example.server.controller;

import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class JsonController {

    @PostMapping("/saveJson")
    public String saveJson(@RequestBody String jsonData) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(jsonData);
            return "JSON data saved successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving JSON data.";
        }
    }
}