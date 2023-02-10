package com.petcorner.animaltrainer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcorner.animaltrainer.model.Trainer;
import com.petcorner.animaltrainer.model.AnimalTrainerModel;
import com.petcorner.animaltrainer.queue.CustomMessage;
import com.petcorner.animaltrainer.queue.MQConfig;
import com.petcorner.animaltrainer.repository.AnimalTrainingRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
@CrossOrigin
@RestController
@RequestMapping("/trainer/v2")
public class AnimalTrainerController {
    /**
     *
     */
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private AnimalTrainingRepository repository;
    @Autowired
    private AnimalTrainerModel model;

    @GetMapping("/trainers")
    public List<Trainer> getTrainers(){
        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;
    }

    @GetMapping("/trainers/sort_by/{sortMethod}")
    public List<Trainer> getTrainersOrdered(@PathVariable String sortMethod){

        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (sortMethod) {
            case "age_increase" -> {
                response.sort(Comparator.comparing(Trainer::getAge));
                return response;
            }
            case "age_decrease" -> {
                response.sort(Comparator.comparing(Trainer::getAge).reversed());
                return response;
            }
            case "sizeAllowed_increase" -> {
                response.sort(Comparator.comparing(Trainer::getSizeAllowed));
                return response;
            }
            case "sizeAllowed_decrease" -> {
                response.sort(Comparator.comparing(Trainer::getSizeAllowed).reversed());
                return response;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/trainers/filter/{filter}/{filterValueMin}/{filterValueMax}")
    public List<Trainer> getSittersFiltered(@PathVariable String filter,
                                           @PathVariable int filterValueMin, @PathVariable int filterValueMax){

        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        switch (filter) {
            case "sizeAllowed" -> {
                response = repository.findBySizeAllowedBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Trainer::getSizeAllowed));
                return response;
            }
            case "age" -> {
                response = repository.findByAgeBetween(filterValueMin, filterValueMax);
                response.sort(Comparator.comparing(Trainer::getSizeAllowed));
                return response;
            }

            default -> throw new IllegalArgumentException();
        }
    }

    @GetMapping("/trainers/locality/{locality}")
    public List<Trainer> getSittersFilteredByLocality(@PathVariable String locality){

        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findByLocality(locality));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;



    }

    @GetMapping("/trainers/animalAllowed/{animalAllowed}")
    public List<Trainer> getTrainersByAnimalAllowed(@PathVariable String animalAllowed){
        List<Trainer> response;

        try {
            response = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        return response;

    }


    @GetMapping("/trainers/search-by-animalAllowed/{animalAllowed}")
    public List<Trainer> getTrainersBySearchAnimalAllowed(@PathVariable String animalAllowed) {
        List<Trainer> response;

        try {
            response = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
        return response;

    }

    ///////////////v2

    @GetMapping("/trainers/page/{page}")
    public List<Trainer> getTrainersForPage(@PathVariable int page){
        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        response = model.trainersForNPage(response, page);
        return response;
    }

    @GetMapping("/trainers/page-number")
    public int getPagesNumber(){
        List<Trainer> response;
        try {
            response = new ArrayList<>(repository.findAll());
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return 0;
        }
        int pages = (response.size()/20)+1;
        return pages;
    }

    @GetMapping("trainers/sort_by/{sortMethod}/page/{page}")
    public List<Trainer> getSittersOrdered(@PathVariable String sortMethod, @PathVariable int page){

        List<Trainer> response = new ArrayList<>(model.orderList(sortMethod));
        return model.trainersForNPage(response, page);
    }

    //Non funziona questo metodo
    @GetMapping("/trainers/filter/{filter}/{filterValueMin}/{filterValueMax}/page/{page}")
    public Map<Integer,List<Trainer>> getSittersFiltered(@PathVariable String filter,
                                                        @PathVariable int filterValueMin, @PathVariable int filterValueMax,
                                                        @PathVariable int page){

        List<Trainer> trainers = new ArrayList<>(model.listFiltered(filter, filterValueMin, filterValueMax));
        List<Trainer> trainersForPage= new ArrayList<>(model.trainersForNPage(trainers,page));
        int pages = (trainers.size()/20)+1;

        Map<Integer, List<Trainer>> response = new HashMap<>();
        response.put(pages,trainersForPage);
        return response;
    }

    //questo metodo non funziona
    @GetMapping("/trainers/locality/{locality}/page/{page}")
    public Map<Integer,List<Trainer>> getTrainersFilteredByProvenance(@PathVariable String locality, @PathVariable int page){

        List<Trainer> trainers;
        try {
            trainers = new ArrayList<>(repository.findByLocalityIgnoreCase(locality));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Trainer> sittersForPage= new ArrayList<>(model.trainersForNPage(trainers,page));
        int pages = (trainers.size()/20)+1;

        Map<Integer,List<Trainer>> response = new HashMap<Integer, List<Trainer>>();
        response.put(pages,sittersForPage);
        return response;

    }

    @GetMapping("/trainers/provenances")
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

    @GetMapping("/trainers/animalAllowed/{animalAllowed}/page/{page}")
    public Map<Integer,List<Trainer>> getTrainersByAnimalAllowed(@PathVariable String animalAllowed, @PathVariable int page){

        List<Trainer> trainers;
        try {
            trainers = new ArrayList<>(repository.findByAnimalsAllowed(animalAllowed));
        } catch (Exception e){
            System.out.println("Error:"+ e.getMessage());
            return null;
        }
        List<Trainer> sittersForPage= new ArrayList<>(model.trainersForNPage(trainers,page));
        int pages = (trainers.size()/20)+1;

        Map<Integer,List<Trainer>> response = new HashMap<Integer, List<Trainer>>();
        response.put(pages,sittersForPage);
        return response;

    }

    //fare metodo per aggiunta Sitter postMapping
    // @PostMapping("/trainers/add")
    // public Trainer addTrainer(@RequestBody Trainer trainer){
    //     try {
    //         repository.save(trainer);
    //     } catch (Exception e){
    //         System.out.println("Error:"+ e.getMessage());
    //         return null;
    //     }
    //     return trainer;
    // }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CustomMessage message) throws IOException {

        switch (message.getMessage()) {
            case "Create animal trainer":
                String trainerString = message.getData().toString();
                System.out.println(message.getData().toString());
                Trainer animalTrainer = new ObjectMapper().readValue(trainerString, Trainer.class);


                System.out.println(animalTrainer);
                try {
                    repository.save(animalTrainer);
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                    break;
                }
                message.setMessage("Create Animal Trainer");
                message.setMessageId(UUID.randomUUID().toString());
                message.setMessageDate(new Date());
                template.convertAndSend(MQConfig.EXCHANGE,
                        MQConfig.ROUTING_KEY, message);

                break;
            case "Delete animal Trainer":
                String trainerStringToDelete = message.getData().toString();
                System.out.println(message.getData().toString());
                Trainer trainerToDelete = new ObjectMapper().readValue(trainerStringToDelete, Trainer.class);

                Long id = trainerToDelete.getId();
                System.out.println(trainerToDelete);
                try {
                    repository.deleteById(id);
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                    break;
                }
                message.setMessage("Trainer Deleted");
                message.setMessageId(UUID.randomUUID().toString());
                message.setMessageDate(new Date());
                template.convertAndSend(MQConfig.EXCHANGE,
                        MQConfig.ROUTING_KEY, message);

                break;


        }
    }
}
