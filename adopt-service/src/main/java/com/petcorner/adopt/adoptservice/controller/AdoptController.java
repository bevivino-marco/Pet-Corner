package com.petcorner.adopt.adoptservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1") //specificare la versione del codice
public class AdoptController {


    @GetMapping("/animals")
    public String getAnimals(){

        return "ciao";
    }

}
