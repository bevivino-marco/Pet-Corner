package com.petcorner.profileservice.profileservice.repository;

import com.petcorner.profileservice.profileservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String roleName);
}
