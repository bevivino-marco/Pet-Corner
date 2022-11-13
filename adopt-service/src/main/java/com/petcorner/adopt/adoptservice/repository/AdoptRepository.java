package com.petcorner.adopt.adoptservice.repository;

import com.petcorner.adopt.adoptservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptRepository extends JpaRepository<Animal, Long> {

    List<Animal>  findBySizeBetween(int filterValueMin, int filterValueMax);

    List<Animal> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<Animal>  findByProvenance(String filterValue);

}
