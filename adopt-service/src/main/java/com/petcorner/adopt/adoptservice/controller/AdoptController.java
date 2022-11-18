package com.petcorner.adopt.adoptservice.controller;

import com.petcorner.adopt.adoptservice.model.Animal;
import com.petcorner.adopt.adoptservice.repository.AdoptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AdoptController {

    @Autowired
    public AdoptRepository repository;


    @GetMapping("/animals")
    public List<Animal> getAnimals(){
        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;
    }


    @GetMapping("/animals/sort_by/{sortMethod}")
    public List<Animal> getAnimalsOrdered(@PathVariable String sortMethod){

        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (sortMethod) {
            case "age_increase" -> {
                response.sort(Comparator.comparing(Animal::getAge));
                return response;
            }
            case "age_decrease" -> {
                response.sort(Comparator.comparing(Animal::getAge).reversed());
                return response;
            }
            case "size_increase" -> {
                response.sort(Comparator.comparing(Animal::getSize));
                return response;
            }
            case "size_decrease" -> {
                response.sort(Comparator.comparing(Animal::getSize).reversed());
                return response;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/animals/filter/{filter}/{filterValueMin}/{filterValueMax}")
    public List<Animal> getAnimalsFiltered(@PathVariable String filter,
                                           @PathVariable int filterValueMin, @PathVariable int filterValueMax){

        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (filter) {
            case "size" -> {
                response = repository.findBySizeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Animal::getSize));
                return response;
            }
            case "age" -> {
                response = repository.findByAgeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Animal::getSize));
                return response;
            }

            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/animals/provenance/{provenance}")
    public List<Animal> getAnimalsFilteredByProvenance(@PathVariable String provenance){

        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findByProvenance(provenance));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;



    }

    @GetMapping("/animals/type/{type}")
    public List<Animal> getAnimalsByType(@PathVariable String type){
        List<Animal> response;

            try {
                response = new ArrayList<>(repository.findByType(type));
            } catch (Exception e){
                System.out.println("Error:"+ e.getMessage());
                return null;
            }
            return response;

    }


    @GetMapping("/animals/search-by-type/{type}")
    public List<Animal> getAnimalsBySearchType(@PathVariable String type) {
        List<Animal> response;

        try {
            response = new ArrayList<>(repository.findByType(type));
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
        return response;

    }




}
