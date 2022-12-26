package com.petcorner.animaltrainer;

import com.petcorner.animaltrainer.model.Trainer;
import com.petcorner.animaltrainer.repository.AnimalTrainingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnimalTrainerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalTrainerApplication.class, args);
	}
	@Bean
	CommandLineRunner run (AnimalTrainingRepository animalTrainingRepository) {
		return args -> {
			animalTrainingRepository.save(new Trainer("Nicole", "Morando", 25, "Roma", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 0, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Bevivino", 27, "Bolzano", "esperto di cani", "dog", 3, "training"));
			animalTrainingRepository.save(new Trainer("Alessandro", "Muraro", 24, "Ovada", "fin da piccolo avevo una passione per i pesci che coltivo ancora adesso", "fish", 0, "training"));
			animalTrainingRepository.save(new Trainer("Marianna", "Rossi", 27, "Venezia", "gattara dalla nascita", "cat", 0, "training"));
			animalTrainingRepository.save(new Trainer("Giovanni", "Rossi", 32, "Cosenza", "esperto di rettili", "reptile", 3, "training"));
			animalTrainingRepository.save(new Trainer("Mario", "Rossi", 40, "Messina", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Asya", "Mantovani", 23, "Torino", "Fin da piccola ho sempre avuto una passione per gli animali, in particolare i cani. Possiedo da 11 anni un barboncino e da qualche mese un dogo argentino", "dog_cat", 3, "training"));
			animalTrainingRepository.save(new Trainer("Chiara", "Bosso", 51, "Salerno", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Maurizio", "Pinna", 28, "Cabras", "gattaro dalla nascita", "cat", 0, "training"));
			animalTrainingRepository.save(new Trainer("Benedetto", "Manasseri", 30, "Milano", "Ho sempre avuto roditori infatti possiedo porcellini d'india, cavie e criceti", "rodents", 0, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Mezzolla", 26, "Lecce", "fin da piccolo avevo una passione per i pesci che coltivo ancora adesso", "fish", 0, "training"));
			animalTrainingRepository.save(new Trainer("Silvia", "Olivieri", 33, "Lecco", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 3, "training"));
			animalTrainingRepository.save(new Trainer("Miriam", "Lo Preti", 20, "Termoli", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 0, "training"));
			animalTrainingRepository.save(new Trainer("Giulia", "Bosso", 51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Federica", "Bosso", 20, "Ancona", "esperienza in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Giovanni", "Bosso", 45, "Aosta", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Matteo", "Zaccarello", 24, "Alassio", "esperienza in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Matteo", "Bua", 48, "Alessandria", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Mirko", "Beatrice", 40, "Salerno", "esperienza di 20 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Mantovani", 53, "Casale Monferrato", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Costanza", "Bosso", 51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Chiara", "Marconi", 51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Bosso", 51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Alessandro", "Bosso", 51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Mauro", "Bosso", 51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Andrea", "Bosso", 51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Luca", "Bosso", 51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Miriam", "Bosso", 51, "Roma", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Giada", "Bosso", 51, "Napoli", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Gaia", "Bosso", 51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Monica", "Bosso", 51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Chiara", "Gervasio", 51, "Pisa", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Chiara", "Mortero", 51, "Pisa", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Giursto", 27, "Trieste", "esperto di cani", "dog", 3, "training"));
			animalTrainingRepository.save(new Trainer("Giulia", "Bevivino", 21, "Trieste", "esperto di cani", "dog", 3, "training"));
			animalTrainingRepository.save(new Trainer("Marco", "Bevivino", 26, "Bergamo", "esperto di cani", "dog", 3, "training"));
		};
	}
}
