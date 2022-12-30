package com.petcorner.animaltherapy.model;

import com.petcorner.animaltherapy.repository.AnimalTherapyRepository;
import com.petcorner.animaltherapy.model.AnimalTherapy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AnimalTherapyModel {
    @Autowired
    private AnimalTherapyRepository repository;

    private static int ELEMENT_FOR_PAGE= 20;

    public List<AnimalTherapy> animalsTherapyForNPage(List<AnimalTherapy> list, int page){
        int firstAnimal=(page-1)*ELEMENT_FOR_PAGE;
        if (firstAnimal> list.size()){
            throw new IllegalArgumentException("Page number not exist");
        }
        int lastAnimal=page*ELEMENT_FOR_PAGE;
        if(list.size()> lastAnimal) {
            System.out.println("Non ultima pagina");

            return list.subList(firstAnimal, lastAnimal);
        }else{
            System.out.println("ultima pagina");
            return list.subList(firstAnimal, list.size());

        }
    }

    public List<AnimalTherapy> orderList(String sortMethod) {
        List<AnimalTherapy> response = new ArrayList<>();
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

    public List<AnimalTherapy> listFiltered(String filter, int filterValueMin, int filterValueMax) {
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
}
