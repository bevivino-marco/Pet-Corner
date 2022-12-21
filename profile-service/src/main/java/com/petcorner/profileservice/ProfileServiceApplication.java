package com.petcorner.profileservice;

import com.petcorner.profileservice.model.Role;
import com.petcorner.profileservice.model.User;
import com.petcorner.profileservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (UserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_TRAINEE"));
			userService.saveRole(new Role(null, "ROLE_BUSINESS"));
			userService.saveRole(new Role(null, "ROLE_SITTER"));

			userService.saveUser(new User(null, "Jhon Travolta",
					"jhon@gmail.com", "1234", new ArrayList<>()
			));
			userService.saveUser(new User(null, "Marco Bevivino",
					"marco@gmail.com", "1234", new ArrayList<>()
			));
			userService.saveUser(new User(null, "Asya Mantovani",
					"asya@gmail.com", "1234", new ArrayList<>()
			));
			userService.saveUser(new User(null, "Alessandro Muraro",
					"alessandro@gmail.com", "1234", new ArrayList<>()
			));


			userService.addRoleToUser("jhon@gmail.com", "ROLE_USER");
			userService.addRoleToUser("marco@gmail.com", "ROLE_TRAINEE");
			userService.addRoleToUser("marco@gmail.com", "ROLE_SITTER");
			userService.addRoleToUser("asya@gmail.com", "ROLE_BUSINESS");
			userService.addRoleToUser("alessandro@gmail.com", "ROLE_SITTER");

		};
	}


}
