package com.petcorner.animalsitting.animalsitting.repository;

import com.petcorner.animalsitting.animalsitting.model.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalSittingRepository extends JpaRepository<Sitter, Long> {
    List<Sitter> findBySizeAllowedBetween(int filterValueMin, int filterValueMax);

    List<Sitter> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<Sitter>  findByLocality(String filterValue);
    @Query("select distinct locality from Sitter")
    List<String>  findDistinctByLocality();


    List<Sitter> findByAnimalsAllowed(String animalAllowed);

    List<Sitter>  findByLocalityIgnoreCase(String locality);

    Sitter findByOwner(String owner);
}
