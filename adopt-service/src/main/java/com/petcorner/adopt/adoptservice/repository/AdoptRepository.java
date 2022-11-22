package com.petcorner.adopt.adoptservice.repository;

import com.petcorner.adopt.adoptservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptRepository extends JpaRepository<Animal, Long> {

    List<Animal>  findBySizeBetween(int filterValueMin, int filterValueMax);

    List<Animal> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<Animal>  findByProvenance(String filterValue);
    @Query("select distinct provenance from Animal")
    List<String>  findDistinctByProvenance();


    List<Animal>  findByType(String type);

    List<Animal>  findByProvenanceIgnoreCase(String provenance);
}
