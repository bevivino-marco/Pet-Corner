package com.petcorner.adopt.adoptservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com/petcorner/adopt/adoptservice"})
public class AdoptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptServiceApplication.class, args);
	}

}
