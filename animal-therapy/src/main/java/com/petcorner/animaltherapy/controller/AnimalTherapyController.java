package com.petcorner.animaltherapy.controller;

import com.petcorner.animaltherapy.model.AnimalTherapy;
import com.petcorner.animaltherapy.model.AnimalTherapyModel;
import com.petcorner.animaltherapy.repository.AnimalTherapyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AnimalTherapyController {
    @Autowired
    private AnimalTherapyRepository repository;
    @Autowired
    private AnimalTherapyModel model;

    @GetMapping("/animalsTherapy")
    public List<AnimalTherapy> getAnimalsTherapy(){
        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;
    }




    @GetMapping("/animalsTherapy/sort_by/{sortMethod}")
    public List<AnimalTherapy> getAnimalsTherapyOrdered(@PathVariable String sortMethod){

        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (sortMethod) {
            case "age_increase" -> {
                response.sort(Comparator.comparing(AnimalTherapy::getAge));
                return response;
            }
            case "age_decrease" -> {
                response.sort(Comparator.comparing(AnimalTherapy::getAge).reversed());
                return response;
            }
            case "size_increase" -> {
                response.sort(Comparator.comparing(AnimalTherapy::getSize));
                return response;
            }
            case "size_decrease" -> {
                response.sort(Comparator.comparing(AnimalTherapy::getSize).reversed());
                return response;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/animalsTherapy/filter/{filter}/{filterValueMin}/{filterValueMax}")
    public List<AnimalTherapy> getAnimalsTherapyFiltered(@PathVariable String filter,
                                           @PathVariable int filterValueMin, @PathVariable int filterValueMax){

        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (filter) {
            case "size" -> {
                response = repository.findBySizeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(AnimalTherapy::getSize));
                return response;
            }
            case "age" -> {
                response = repository.findByAgeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(AnimalTherapy::getSize));
                return response;
            }

            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/animalsTherapy/provenance/{provenance}")
    public List<AnimalTherapy> getAnimalsTherapyFilteredByProvenance(@PathVariable String provenance){

        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findByProvenance(provenance));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;



    }

    @GetMapping("/animalsTherapy/type/{type}")
    public List<AnimalTherapy> getAnimalsTherapyByType(@PathVariable String type){
        List<AnimalTherapy> response;

        try {
            response = new ArrayList<>(repository.findByType(type));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;

    }


    @GetMapping("/animalsTherapy/search-by-type/{type}")
    public List<AnimalTherapy> getAnimalsTherapyBySearchType(@PathVariable String type) {
        List<AnimalTherapy> response;

        try {
            response = new ArrayList<>(repository.findByType(type));
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
        return response;

    }

    ///////////////v2

    @GetMapping("/animalsTherapy/page/{page}")
    public List<AnimalTherapy> getAnimalsTherapyForPage(@PathVariable int page){
        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        response = model.animalsTherapyForNPage(response, page);
        return response;
    }

    @GetMapping("/animalsTherapy/page-number")
    public int getPagesNumber(){
        List<AnimalTherapy> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return 0;
        }
        int pages = (response.size()/20)+1;
        return pages;
    }

    @GetMapping("animalsTherapy/sort_by/{sortMethod}/page/{page}")
    public List<AnimalTherapy> getAnimalsTherapyOrdered(@PathVariable String sortMethod, @PathVariable int page){

        List<AnimalTherapy> response = new ArrayList<>(model.orderList(sortMethod));
        return model.animalsTherapyForNPage(response, page);
    }


    @GetMapping("/animalsTherapy/filter/{filter}/{filterValueMin}/{filterValueMax}/page/{page}")
    public Map<Integer,List<AnimalTherapy>> getAnimalsTherapyFiltered(@PathVariable String filter,
                                                        @PathVariable int filterValueMin, @PathVariable int filterValueMax,
                                                        @PathVariable int page){

        List<AnimalTherapy> animalsTherapy = new ArrayList<>(model.listFiltered(filter, filterValueMin, filterValueMax));
        List<AnimalTherapy> animalsTherapyForPage= new ArrayList<>(model.animalsTherapyForNPage(animalsTherapy,page));
        int pages = (animalsTherapy.size()/20)+1;

        Map<Integer,List<AnimalTherapy>> response = new HashMap<Integer, List<AnimalTherapy>>();
        response.put(pages,animalsTherapyForPage);
        return response;
    }

    @GetMapping("/animalsTherapy/provenance/{provenance}/page/{page}")
    public Map<Integer,List<AnimalTherapy>> getAnimalsTherapyFilteredByProvenance(@PathVariable String provenance, @PathVariable int page){

        List<AnimalTherapy> animalsTherapy;
        try {
            animalsTherapy = new ArrayList<>(repository.findByProvenanceIgnoreCase(provenance));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<AnimalTherapy> animalsTherapyForPage= new ArrayList<>(model.animalsTherapyForNPage(animalsTherapy,page));
        int pages = (animalsTherapy.size()/20)+1;

        Map<Integer,List<AnimalTherapy>> response = new HashMap<Integer, List<AnimalTherapy>>();
        response.put(pages,animalsTherapyForPage);
        return response;

    }

    @GetMapping("/animalsTherapy/provenances")
    public List<String> getProvenances(){

        List<String> provenances;
        try {
            provenances = new ArrayList<>(repository.findDistinctByProvenance());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }

        return provenances;

    }

    @GetMapping("/animalsTherapy/type/{type}/page/{page}")
    public Map<Integer,List<AnimalTherapy>> getAnimalsTherapyByType(@PathVariable String type, @PathVariable int page){

        List<AnimalTherapy> animalsTherapy;
        try {
            animalsTherapy = new ArrayList<>(repository.findByType(type));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<AnimalTherapy> animalsTherapyForPage= new ArrayList<>(model.animalsTherapyForNPage(animalsTherapy,page));
        int pages = (animalsTherapy.size()/20)+1;

        Map<Integer,List<AnimalTherapy>> response = new HashMap<Integer, List<AnimalTherapy>>();
        response.put(pages,animalsTherapyForPage);
        return response;

    }


    @PostMapping("/animalsTherapy/add")
    public AnimalTherapy addAnimalTherapy(@RequestBody AnimalTherapy animalTherapy){
        try {
            repository.save(animalTherapy);
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return animalTherapy;
    }
}
