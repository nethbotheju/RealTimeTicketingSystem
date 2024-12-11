package com.example.server;

import com.example.server.cli.ServerSocketCLI;
import com.example.server.database.DatabaseSetup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ServerSocketCLI.connect();
		DatabaseSetup.connect();
		SpringApplication.run(ServerApplication.class, args);
	}

}
