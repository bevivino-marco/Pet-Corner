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

	@Bean
	 CommandLineRunner run (AnimalSittingRepository animalSittingRepository) {
		return args -> {
			animalSittingRepository.save(new Sitter("Nicole", "Morando",25, "Roma", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 0));
			animalSittingRepository.save(new Sitter("Marco", "Bevivino",27, "Bolzano", "esperto di cani", "dog", 3));
			animalSittingRepository.save(new Sitter("Alessandro", "Muraro",24, "Ovada", "fin da piccolo avevo una passione per i pesci che coltivo ancora adesso", "fish", 0));
			animalSittingRepository.save(new Sitter("Marianna", "Rossi",27, "Venezia", "gattara dalla nascita", "cat", 0));
			animalSittingRepository.save(new Sitter("Giovanni", "Rossi",32, "Cosenza", "esperto di rettili", "reptile", 3));
			animalSittingRepository.save(new Sitter("Mario", "Rossi",40, "Messina", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Asya", "Mantovani",23, "Torino", "Fin da piccola ho sempre avuto una passione per gli animali, in particolare i cani. Possiedo da 11 anni un barboncino e da qualche mese un dogo argentino", "dog_cat", 3));
			animalSittingRepository.save(new Sitter("Chiara", "Bosso",51, "Salerno", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Maurizio", "Pinna",28, "Cabras", "gattaro dalla nascita", "cat", 0));
			animalSittingRepository.save(new Sitter("Bendetto", "Manasseri",30, "Milano", "Ho sempre avuto roditori infatti possiedo porcellini d'india, cavie e criceti", "rodents", 0));
			animalSittingRepository.save(new Sitter("Marco", "Mezzolla",26, "Lecce", "fin da piccolo avevo una passione per i pesci che coltivo ancora adesso", "fish", 0));
			animalSittingRepository.save(new Sitter("Silvia", "Olivieri",33, "Lecco", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 3));
			animalSittingRepository.save(new Sitter("Miriam", "Lo Preti",20, "Termoli", "esperto di rapaci e uccelli", "birdsOfPrey_birds", 0));
			animalSittingRepository.save(new Sitter("Giulia", "Bosso",51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Federica", "Bosso",20, "Ancona", "esperienza in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Giovanni", "Bosso",45, "Aosta", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Matteo", "Zaccarello",24, "Alassio", "esperienza in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Matteo", "Bua",48, "Alessandria", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Mirko", "Beatrice",40, "Salerno", "esperienza di 20 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Marco", "Mantovani",53, "Casale Monferrato", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Costanza", "Bosso",51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Chiara", "Marconi",51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Marco", "Bosso",51, "Milano", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Alessandro", "Bosso",51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Mauro", "Bosso",51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Andrea", "Bosso",51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Luca", "Bosso",51, "Torino", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Miriam", "Bosso",51, "Roma", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Giada", "Bosso",51, "Napoli", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Gaia", "Bosso",51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Monica", "Bosso",51, "Firenze", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Chiara", "Gervasio",51, "Pisa", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Chiara", "Mortero",51, "Pisa", "esperienza di 30 anni in mansioni di animal sitting", "dog_cat_reptile_birdsOfPrey_birds_rodents", 3));
			animalSittingRepository.save(new Sitter("Marco", "Giursto",27, "Trieste", "esperto di cani", "dog", 3));
			animalSittingRepository.save(new Sitter("Giulia", "Bevivino",21, "Trieste", "esperto di cani", "dog", 3));
			animalSittingRepository.save(new Sitter("Marco", "Bevivino",26, "Bergamo", "esperto di cani", "dog", 3));
		};
	}
}
