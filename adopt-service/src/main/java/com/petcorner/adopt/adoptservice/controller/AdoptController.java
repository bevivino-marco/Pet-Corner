package com.petcorner.adopt.adoptservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcorner.adopt.adoptservice.model.AdoptModel;
import com.petcorner.adopt.adoptservice.model.Animal;
import com.petcorner.adopt.adoptservice.proxy.ProfileServiceProxy;
import com.petcorner.adopt.adoptservice.queue.CustomMessage;
import com.petcorner.adopt.adoptservice.queue.MQConfig;
import com.petcorner.adopt.adoptservice.repository.AdoptRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/adopt/v2")
public class AdoptController {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private AdoptRepository repository;
    @Autowired
    private AdoptModel model;
    @Autowired
    private ProfileServiceProxy proxy;

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


    @GetMapping("/animals/owner/{username}")
    public List<Animal> getAnimalsByOwner(@PathVariable String username){
        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findByOwner(username));
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

    ///////////////v2

    @GetMapping("/animals/page/{page}")
    public List<Animal> getAnimalsForPage(@PathVariable int page){
        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        response = model.animalsForNPage(response, page);
        return response;
    }

    @GetMapping("/animals/page-number")
    public int getPagesNumber(){
        List<Animal> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return 0;
        }
        int pages = (response.size()/20)+1;
        return pages;
    }

    @GetMapping("animals/sort_by/{sortMethod}/page/{page}")
    public List<Animal> getAnimalsOrdered(@PathVariable String sortMethod, @PathVariable int page){

        List<Animal> response = new ArrayList<>(model.orderList(sortMethod));
        return model.animalsForNPage(response, page);
    }


    @GetMapping("/animals/filter/{filter}/{filterValueMin}/{filterValueMax}/page/{page}")
    public Map<Integer,List<Animal>> getAnimalsFiltered(@PathVariable String filter,
                                                  @PathVariable int filterValueMin, @PathVariable int filterValueMax,
                                                  @PathVariable int page){

            List<Animal> animals = new ArrayList<>(model.listFiltered(filter, filterValueMin, filterValueMax));
            List<Animal> animalsForPage= new ArrayList<>(model.animalsForNPage(animals,page));
            int pages = (animals.size()/20)+1;

            Map<Integer,List<Animal>> response = new HashMap<Integer, List<Animal>>();
            response.put(pages,animalsForPage);
            return response;
    }

    @GetMapping("/animals/provenance/{provenance}/page/{page}")
    public Map<Integer,List<Animal>> getAnimalsFilteredByProvenance(@PathVariable String provenance, @PathVariable int page){

        List<Animal> animals;
        try {
            animals = new ArrayList<>(repository.findByProvenanceIgnoreCase(provenance));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Animal> animalsForPage= new ArrayList<>(model.animalsForNPage(animals,page));
        int pages = (animals.size()/20)+1;

        Map<Integer,List<Animal>> response = new HashMap<Integer, List<Animal>>();
        response.put(pages,animalsForPage);
        return response;

    }

    @GetMapping("/animals/provenances")
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

    @GetMapping("/animals/type/{type}/page/{page}")
    public Map<Integer,List<Animal>> getAnimalsByType(@PathVariable String type, @PathVariable int page){

        List<Animal> animals;
        try {
            animals = new ArrayList<>(repository.findByType(type));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Animal> animalsForPage= new ArrayList<>(model.animalsForNPage(animals,page));
        int pages = (animals.size()/20)+1;

        Map<Integer,List<Animal>> response = new HashMap<Integer, List<Animal>>();
        response.put(pages,animalsForPage);
        return response;

    }



    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CustomMessage message) throws IOException {

        switch(message.getMessage()) {
            case "Create animal":
                String animalString = message.getData().toString();
                System.out.println(message.getData().toString());
                Animal animal = new ObjectMapper().readValue(animalString, Animal.class);


                System.out.println(animal);
                try {
                    repository.save(animal);
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                    return;
                }
                message.setMessage("Animal Created");
                message.setMessageId(UUID.randomUUID().toString());
                message.setMessageDate(new Date());
                template.convertAndSend(MQConfig.EXCHANGE,
                        MQConfig.ROUTING_KEY, message);

                break;
            case "Delete animal":
                String animalStringToDelete = message.getData().toString();
                System.out.println(message.getData().toString());
                Animal animalToDelete = new ObjectMapper().readValue(animalStringToDelete, Animal.class);

                Long id = animalToDelete.getId();
                System.out.println(animalToDelete);
                try {
                    repository.deleteById(id);
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                    return;
                }
                message.setMessage("Animal Deleted");
                message.setMessageId(UUID.randomUUID().toString());
                message.setMessageDate(new Date());
                template.convertAndSend(MQConfig.EXCHANGE,
                        MQConfig.ROUTING_KEY, message);

                break;


        }
    }


}
