package com.petcorner.profileservice.profileservice;

import com.petcorner.profileservice.profileservice.model.Role;
import com.petcorner.profileservice.profileservice.model.User;
import com.petcorner.profileservice.profileservice.model.UserService;
import com.petcorner.profileservice.profileservice.model.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication()
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(UserServiceImpl service){

		return args -> {
			service.saveRole(new Role(null, "SITTER"));
			service.saveRole(new Role(null, "TRAINER"));
			service.saveRole(new Role(null, "KENNEL"));

			service.saveUser(new User("Marco",
					"Bevivino",
					"paperino",
					"ighiu1547802",
					"nvsjfl23409",
					"Torino",
					"email@email.com"));
			service.saveUser(new User("Alessandro",
					"Muraro",
					"paperina",
					"ighiu1547802",
					"nvsjfl23409",
					"Torino",
					"email2@email.com"));



			service.addRoleToUser("email2@email.com", "SITTER");

			};
	}


}
