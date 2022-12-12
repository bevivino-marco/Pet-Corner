package com.petcorner.profileservice.profileservice;

import com.petcorner.profileservice.profileservice.model.Role;
import com.petcorner.profileservice.profileservice.model.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication()
@ComponentScan({"com.petcorner.profileservice.profileservice.security","com.petcorner.profileservice.profileservice.dao"})
public class ProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
	}



}
