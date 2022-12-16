package com.petcorner.profileservice.profileservice.repository;


import com.petcorner.profileservice.profileservice.model.PetCornerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PetCornerUser, String> {
    Optional<PetCornerUser> findByUsername(String username);
    boolean existsByUsername(String username);
}