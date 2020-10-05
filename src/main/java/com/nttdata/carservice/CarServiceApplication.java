package com.nttdata.carservice;

import com.nttdata.carservice.datastorage.AutomobileDataStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		AutomobileDataStorage.getAutomobilesFromFile();
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
