package com.nttdata.carservice;

import com.nttdata.carservice.storage.AutomobileDataStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.HashMap;

/**
 * Main class to run Application
 *
 * @author "Amos Gross"
 * @version 0.0.1
 */

@SpringBootApplication
public class CarServiceApplication {

	@Bean
	AutomobileDataStorage automobileDataStorage(){
		return new AutomobileDataStorage(null);
	}

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
