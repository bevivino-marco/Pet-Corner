package com.petcorner.animaltherapy.repository;

import com.petcorner.animaltherapy.model.AnimalTherapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalTherapyRepository extends JpaRepository<AnimalTherapy, Long> {
    List<AnimalTherapy> findBySizeBetween(int filterValueMin, int filterValueMax);

    List<AnimalTherapy> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<AnimalTherapy>  findByProvenance(String filterValue);
    @Query("select distinct provenance from AnimalTherapy")
    List<String>  findDistinctByProvenance();


    List<AnimalTherapy>  findByType(String type);

    List<AnimalTherapy>  findByProvenanceIgnoreCase(String provenance);

    List<AnimalTherapy> findByOwner(String owner);
}
