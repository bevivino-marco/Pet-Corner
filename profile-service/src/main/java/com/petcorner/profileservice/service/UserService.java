package com.petcorner.profileservice.service;

import com.petcorner.profileservice.model.Role;
import com.petcorner.profileservice.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List <User> getUsers();
}
