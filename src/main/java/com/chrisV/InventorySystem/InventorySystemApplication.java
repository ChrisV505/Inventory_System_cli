package com.chrisV.InventorySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@CommandScan
@SpringBootApplication
public class InventorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorySystemApplication.class, args);

	}

}
