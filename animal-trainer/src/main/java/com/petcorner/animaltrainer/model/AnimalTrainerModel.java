package com.petcorner.animaltrainer.model;

import com.petcorner.animaltrainer.repository.AnimalTrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class AnimalTrainerModel {
    /**
     *
     */
    @Autowired
    private AnimalTrainingRepository repository;

    private static int ELEMENT_FOR_PAGE= 20;

    public List<Trainer> trainersForNPage(List<Trainer> list, int page){
        int firstTrainer=(page-1)*ELEMENT_FOR_PAGE;
        if (firstTrainer> list.size()){
            throw new IllegalArgumentException("Page number not exist");
        }
        int lastTrainer=page*ELEMENT_FOR_PAGE;
        if(list.size()> lastTrainer) {
            System.out.println("Non ultima pagina");

            return list.subList(firstTrainer, lastTrainer);
        }else{
            System.out.println("ultima pagina");
            return list.subList(firstTrainer, list.size());

        }
    }

    public List<Trainer> orderList(String sortMethod) {
        List<Trainer> response = new ArrayList<>();
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

    public List<Trainer> listFiltered(String filter, int filterValueMin, int filterValueMax) {
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

}
