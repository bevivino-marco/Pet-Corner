package com.petcorner.animalsitting.animalsitting;

import com.petcorner.animalsitting.animalsitting.model.Sitter;
import com.petcorner.animalsitting.animalsitting.repository.AnimalSittingRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class AnimalSittingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalSittingApplication.class, args);
	}

}
