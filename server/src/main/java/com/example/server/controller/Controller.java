package com.example.server.controller;

import com.example.server.Main;
import com.example.server.model.ConfigTasks;
import org.json.simple.parser.ParseException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class Controller {

    private final SimpMessagingTemplate messagingTemplate;

    public Controller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/start")
    public Boolean start() throws FileNotFoundException {
        String result = Main.start();


        // Send WebSocket message to clients connected to "/topic/updates"
        messagingTemplate.convertAndSend("/topic/start/data", result);

        return true;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/stop")
    public String stop() throws FileNotFoundException {
        Main.stop();

        messagingTemplate.convertAndSend("/topic/stop/data", "Stopped");

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

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/removeCustomer/{id}")
    public String removeCustomer(@PathVariable int id) {
        String result = Main.removeCustomer(id);
        return result;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/loadConfig")
    public String returnConfigFrontend() throws FileNotFoundException {

        return ConfigTasks.loadConfigFrontend();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/updateConfig/")
    public String updateConfigFrontend(@RequestBody String jsonString) throws ParseException {
        ConfigTasks.updateConfig(jsonString);
        return "Updated SuccessFully";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/reset")
    public Boolean reset() throws FileNotFoundException {
        Main.reset();

        return true;
    }
}
