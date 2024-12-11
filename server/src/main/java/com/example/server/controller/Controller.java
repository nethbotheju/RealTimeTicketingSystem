package com.example.server.controller;

import com.example.server.Main;
import com.example.server.cli.ServerSocketCLI;
import com.example.server.database.DatabaseSetup;
import com.example.server.config.ConfigTasks;
import com.example.server.logging.LogConfig;
import org.json.simple.parser.ParseException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    private final SimpMessagingTemplate messagingTemplate;

    public Controller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/start")
    public Boolean start(){
        // Fetch past sales data and send to the frontend
        DatabaseSetup.fetchSales();

        String result = Main.start();

        // Send customers and vendors data using websocket
        messagingTemplate.convertAndSend("/topic/start/data", result);

        // Send a message to the CLI
        ServerSocketCLI.sendMessage("started");

        return true;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/stop")
    public Boolean stop(){
        Main.stop();

        // Send customers and vendors data using websocket
        messagingTemplate.convertAndSend("/topic/stop/data", "Stopped");

        // Send a message to the CLI
        ServerSocketCLI.sendMessage("stopped");

        return true;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/addVendor")
    public String addv() {
        return Main.addVendor();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/addCustomer/{priority}")
    public String addc(@PathVariable("priority") int priority) {
        return Main.addCustomer(priority);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/removeVendor/{id}")
    public String removeVendor(@PathVariable int id) {
        return Main.removeVendor(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/removeCustomer/{id}")
    public String removeCustomer(@PathVariable int id) {
        return Main.removeCustomer(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/loadConfig/")
    public String returnConfigFrontend(){
        ServerSocketCLI.sendMessage("Loaded Configuration from config.json to frontend.");
        LogConfig.logger.info("Loaded Configuration from config.json to frontend.");

        return ConfigTasks.loadConfigFrontend();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/updateConfig/")
    public String updateConfigFrontend(@RequestBody String jsonString) throws ParseException {
        ConfigTasks.updateConfig(jsonString);

        // Delete past sales data in the database
        DatabaseSetup.deleteAllSales();

        ServerSocketCLI.sendMessage("Updated configuration saved to config.json.");
        LogConfig.logger.info("Updated configuration saved to config.json.");

        return "Updated configuration saved to config.json";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/reset")
    public Boolean reset(){
        Main.reset();

        // Delete past sales data in the database
        DatabaseSetup.deleteAllSales();

        return true;
    }

}
