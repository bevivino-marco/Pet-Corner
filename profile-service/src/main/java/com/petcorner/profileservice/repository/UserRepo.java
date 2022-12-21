package com.petcorner.profileservice.repository;

import com.petcorner.profileservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
