package com.petcorner.adopt.adoptservice.controller;

import com.petcorner.adopt.adoptservice.model.Animal;
import com.petcorner.adopt.adoptservice.repository.AdoptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1") //specificare la versione del codice
public class AdoptController {

    @Autowired
    public AdoptRepository repository;


    @GetMapping("/animals")
    public List<Animal> getAnimals(){
        List<Animal> response = new ArrayList<Animal>();
        try {
            response.addAll(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;
    }

}
