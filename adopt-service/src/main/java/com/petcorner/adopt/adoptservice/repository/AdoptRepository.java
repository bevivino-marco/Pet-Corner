package com.petcorner.adopt.adoptservice.repository;

import com.petcorner.adopt.adoptservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdoptRepository extends JpaRepository<Animal, Long> {

    List<Animal>  findBySizeBetween(int filterValueMin, int filterValueMax);

    List<Animal> findByAgeBetween(int filterValueMin, int filterValueMax);

    List<Animal>  findByProvenance(String filterValue);

    List<Animal>  findByOwner(String username);

    void deleteById(Long id);

    @Query("select distinct provenance from Animal")
    List<String>  findDistinctByProvenance();


    List<Animal>  findByType(String type);

    List<Animal>  findByProvenanceIgnoreCase(String provenance);
    @Transactional
    @Modifying
    @Query("delete from Animal where microchip= :microchip")
    void deleteByMicrochip(String microchip);
    @Transactional
    @Modifying
    @Query("DELETE FROM Animal WHERE owner= :owner")
    void deleteAllByOwner(String owner);
}
