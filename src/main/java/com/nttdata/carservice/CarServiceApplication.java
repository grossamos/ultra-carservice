package com.nttdata.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		AutomobileDataStorage.getAutomobilesFromFile();
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
