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
		SpringApplication.run(AnimalSittingApplication.class, args);}
		@Bean
		CommandLineRunner run (AnimalSittingRepository animalSittingRepository) {
			return args -> {
//				animalSittingRepository.save(new Sitter("Nicole", "Morando", 25, "Roma", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 0, "training","mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Marco", "Bevivino", 27, "Bolzano", "esperto di cani", "dog", 3, "training","2mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Alessandro", "Muraro", 24, "Ovada", "fin da piccolo avevo una passione per i pesci che coltivo ancora adesso", "fish", 0, "training","3mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Marianna", "Rossi", 27, "Venezia", "gattara dalla nascita", "cat", 0, "training","4mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Giovanni", "Rossi", 32, "Cosenza", "esperto di rettili", "reptile", 3, "training","5mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Mario", "Rossi", 40, "Messina", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training","6mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Asya", "Mantovani", 23, "Torino", "Fin da piccola ho sempre avuto una passione per gli animali, in particolare i cani. Possiedo da 11 anni un barboncino e da qualche mese un dogo argentino", "dog_cat", 3, "training","7mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Chiara", "Bosso", 51, "Salerno", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training","8mail1@mail.com"));
//				animalSittingRepository.save(new Sitter("Maurizio", "Pinna", 28, "Cabras", "gattaro dalla nascita", "cat", 0, "training","9mail1@mail.com"));
			};








	}

}
