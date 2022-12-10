package com.petcorner.profileservice.profileservice.model;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName );
    User getUser(String email);

}
