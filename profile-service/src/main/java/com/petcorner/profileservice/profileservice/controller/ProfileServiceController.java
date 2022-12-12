package com.petcorner.profileservice.profileservice.controller;


import com.petcorner.profileservice.profileservice.model.Role;
import com.petcorner.profileservice.profileservice.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProfileServiceController {


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @GetMapping("/register")
    public ResponseEntity<String> register(){
        try {

            return new ResponseEntity<String>("WORKS", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<String>("Not Works", HttpStatus.UNAUTHORIZED);
        }
    }




}
