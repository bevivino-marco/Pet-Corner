package com.petcorner.profileservice.feignproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="adopt-service")
public interface AdoptServiceProxy {


    @GetMapping("/api/v1/animal/provafeign")
    public String getUserInfo();

    @PostMapping("/api/v1/animals/add")
    public String addAnimal(@RequestBody Object animal, @RequestHeader (HttpHeaders.AUTHORIZATION) String token);


    @PostMapping("/api/v1/animalsTherapy/add")
    public String addAnimalTherapy(@RequestBody Object animalTherapy, @RequestHeader (HttpHeaders.AUTHORIZATION) String token);

    @PostMapping("/api/v1/animalsSitter/add")
    public String addAnimalSitter(@RequestBody Object animalSitter, @RequestHeader (HttpHeaders.AUTHORIZATION) String token);

    @PostMapping("/api/v1/animalsTrainer/add")
    public String addAnimalTrainer(@RequestBody Object animalTrainer, @RequestHeader (HttpHeaders.AUTHORIZATION) String token);
}
