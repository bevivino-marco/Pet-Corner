package com.petcorner.animaltrainer.repository;

import com.petcorner.animaltrainer.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalTrainingRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findBySizeAllowedBetween(int filterValueMin, int filterValueMax);

    List<Trainer> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<Trainer>  findByLocality(String filterValue);
    @Query("select distinct locality from Trainer")
    List<String> findDistinctByLocality();


    List<Trainer> findByAnimalsAllowed(String animalAllowed);

    List<Trainer>  findByLocalityIgnoreCase(String locality);

    Trainer findByOwner(String owner);
}
