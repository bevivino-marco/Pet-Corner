package com.petcorner.animalsitting.animalsitting.model;

import com.petcorner.animalsitting.animalsitting.repository.AnimalSittingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AnimalSittingModel {
    /**
     *
     */
    @Autowired
    private AnimalSittingRepository repository;

    private static int ELEMENT_FOR_PAGE= 20;

    public List<Sitter> sittersForNPage(List<Sitter> list, int page){
        int firstSitter=(page-1)*ELEMENT_FOR_PAGE;
        if (firstSitter> list.size()){
            throw new IllegalArgumentException("Page number not exist");
        }
        int lastSitter=page*ELEMENT_FOR_PAGE;
        if(list.size()> lastSitter) {
            System.out.println("Non ultima pagina");

            return list.subList(firstSitter, lastSitter);
        }else{
            System.out.println("ultima pagina");
            return list.subList(firstSitter, list.size());

        }
    }

    public List<Sitter> orderList(String sortMethod) {
        List<Sitter> response = new ArrayList<>();
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

    public List<Sitter> listFiltered(String filter, int filterValueMin, int filterValueMax) {
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

}
