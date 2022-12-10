package com.petcorner.profileservice.profileservice.controller;


import com.petcorner.profileservice.profileservice.model.Role;
import com.petcorner.profileservice.profileservice.model.User;
import com.petcorner.profileservice.profileservice.model.UserServiceImpl;
import com.petcorner.profileservice.profileservice.repository.ProfileRepository;
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

    @Autowired
    private UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.saveUser(user);
            return new ResponseEntity<String>(user.toString(), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<String>(user.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/roles")
    public List<Role> gerRoles() {
        try {

            return service.getRoles();
        } catch (Exception e) {

            return null;
        }
    }

    @GetMapping("/user/{email}")
    public User gerUser(@PathVariable String email){
        try {
            System.out.println(service.getUser(email));
            return service.getUser(email);
        } catch (Exception e) {

            return null;
        }
    }



}
