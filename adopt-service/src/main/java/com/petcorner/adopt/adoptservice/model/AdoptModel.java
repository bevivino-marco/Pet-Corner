package com.petcorner.adopt.adoptservice.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptModel {


    private static int ELEMENT_FOR_PAGE= 20;

    public List<Animal> animalsForNPage(List<Animal> list, int page){
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
}
