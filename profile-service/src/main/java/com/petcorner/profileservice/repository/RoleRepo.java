package com.petcorner.profileservice.repository;

import com.petcorner.profileservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
