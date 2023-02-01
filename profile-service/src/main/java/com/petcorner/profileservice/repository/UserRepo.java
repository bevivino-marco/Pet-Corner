package com.petcorner.profileservice.repository;

import com.petcorner.profileservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String email);

}
