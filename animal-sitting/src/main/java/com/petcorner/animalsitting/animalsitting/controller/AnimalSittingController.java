package com.petcorner.animalsitting.animalsitting.controller;

import com.petcorner.animalsitting.animalsitting.model.AnimalSittingModel;
import com.petcorner.animalsitting.animalsitting.model.Sitter;
import com.petcorner.animalsitting.animalsitting.repository.AnimalSittingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AnimalSittingController {
    @Autowired
    private AnimalSittingRepository repository;
    @Autowired
    private AnimalSittingModel model;

    @GetMapping("/sitters")
    public List<Sitter> getSitters(){
        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;
    }

    @GetMapping("/sitters/sort_by/{sortMethod}")
    public List<Sitter> getSittersOrdered(@PathVariable String sortMethod){

        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (sortMethod) {
            case "age_increase" -> {
                response.sort(Comparator.comparing(Sitter::getAge));
                return response;
            }
            case "age_decrease" -> {
                response.sort(Comparator.comparing(Sitter::getAge).reversed());
                return response;
            }
            case "sizeAllowed_increase" -> {
                response.sort(Comparator.comparing(Sitter::getSizeAllowed));
                return response;
            }
            case "sizeAllowed_decrease" -> {
                response.sort(Comparator.comparing(Sitter::getSizeAllowed).reversed());
                return response;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/sitters/filter/{filter}/{filterValueMin}/{filterValueMax}")
    public List<Sitter> getSittersFiltered(@PathVariable String filter,
                                           @PathVariable int filterValueMin, @PathVariable int filterValueMax){

        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (filter) {
            case "sizeAllowed" -> {
                response = repository.findBySizeAllowedBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Sitter::getSizeAllowed));
                return response;
            }
            case "age" -> {
                response = repository.findByAgeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Sitter::getSizeAllowed));
                return response;
            }

            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/sitters/locality/{locality}")
    public List<Sitter> getSittersFilteredByLocality(@PathVariable String locality){

        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findByLocality(locality));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;



    }

    @GetMapping("/sitters/animalAllowed/{animalAllowed}")
    public List<Sitter> getSittersByAnimalAllowed(@PathVariable String animalAllowed){
        List<Sitter> response;

        try {
            response = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;

    }


    @GetMapping("/sitters/search-by-animalAllowed/{animalAllowed}")
    public List<Sitter> getSittersBySearchAnimalAllowed(@PathVariable String animalAllowed) {
        List<Sitter> response;

        try {
            response = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
        return response;

    }

    ///////////////v2

    @GetMapping("/sitters/page/{page}")
    public List<Sitter> getSittersForPage(@PathVariable int page){
        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        response = model.sittersForNPage(response, page);
        return response;
    }

    @GetMapping("/sitters/page-number")
    public int getPagesNumber(){
        List<Sitter> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return 0;
        }
        int pages = (response.size()/20)+1;
        return pages;
    }

    @GetMapping("/sitters/sort_by/{sortMethod}/page/{page}")
    public List<Sitter> getSittersOrdered(@PathVariable String sortMethod, @PathVariable int page){

        List<Sitter> response = new ArrayList<>(model.orderList(sortMethod));
        return model.sittersForNPage(response, page);
    }

    @GetMapping("/sitters/filter/{filter}/{filterValueMin}/{filterValueMax}/page/{page}")
    public Map<Integer,List<Sitter>> getSittersFiltered(@PathVariable String filter,
                                                        @PathVariable int filterValueMin, @PathVariable int filterValueMax,
                                                        @PathVariable int page){

        List<Sitter> sitters = new ArrayList<>(model.listFiltered(filter, filterValueMin, filterValueMax));
        List<Sitter> sittersForPage= new ArrayList<>(model.sittersForNPage(sitters,page));
        int pages = (sitters.size()/20)+1;

        Map<Integer, List<Sitter>> response = new HashMap<>();
        response.put(pages,sittersForPage);
        return response;
    }

    @GetMapping("/sitters/locality/{locality}/page/{page}")
    public Map<Integer,List<Sitter>> getSittersFilteredByProvenance(@PathVariable String locality, @PathVariable int page){

        List<Sitter> sitters;
        try {
            sitters = new ArrayList<>(repository.findByLocalityIgnoreCase(locality));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Sitter> sittersForPage= new ArrayList<>(model.sittersForNPage(sitters,page));
        int pages = (sitters.size()/20)+1;

        Map<Integer,List<Sitter>> response = new HashMap<Integer, List<Sitter>>();
        response.put(pages,sittersForPage);
        return response;

    }

    @GetMapping("/sitters/provenances")
    public List<String> getProvenances(){

        List<String> provenances;
        try {
            provenances = new ArrayList<>(repository.findDistinctByLocality());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }

        return provenances;

    }

    @GetMapping("/sitters/animalAllowed/{animalAllowed}/page/{page}")
    public Map<Integer,List<Sitter>> getSittersByAnimalAllowed(@PathVariable String animalAllowed, @PathVariable int page){

        List<Sitter> sitters;
        try {
            sitters = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Sitter> sittersForPage= new ArrayList<>(model.sittersForNPage(sitters,page));
        int pages = (sitters.size()/20)+1;

        Map<Integer,List<Sitter>> response = new HashMap<Integer, List<Sitter>>();
        response.put(pages,sittersForPage);
        return response;

    }

    @PostMapping("/sitters/add")
    public Sitter addSitter(@RequestBody Sitter sitter){
        try {
            repository.save(sitter);
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return sitter;
    }
}
