package com.petcorner.adopt.adoptservice.repository;

import com.petcorner.adopt.adoptservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptRepository extends JpaRepository<Animal, Long> {
}
