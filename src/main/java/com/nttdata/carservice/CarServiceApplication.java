package com.nttdata.carservice;

import com.nttdata.carservice.storage.AutomobileDataStorage;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
		try {
			InetAddress giriAddress = InetAddress.getByName("postgres-k8s-service");
			System.out.println(giriAddress.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("FUUUUCKKKKKKKK");
			e.printStackTrace();
		}

		SpringApplication.run(CarServiceApplication.class, args);
	}

}
