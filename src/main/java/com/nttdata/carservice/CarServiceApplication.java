package com.nttdata.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

@SpringBootApplication
public class CarServiceApplication {

	@Bean
	AutomobileDataStorage automobileDataStorage(){
		return new AutomobileDataStorage(new HashMap<>(), new File("./src/main/resources/static/automobiles.json"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
