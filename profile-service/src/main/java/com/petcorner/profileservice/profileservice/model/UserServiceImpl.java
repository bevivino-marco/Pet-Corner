package com.petcorner.profileservice.profileservice.model;

import com.petcorner.profileservice.profileservice.repository.ProfileRepository;
import com.petcorner.profileservice.profileservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private ProfileRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepo.findByEmail(email);
        Role role = roleRepo.findByRoleName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);


    }

    @Override
    public User getUser(String email) {
        return userRepo.findByEmail(email);
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority>authorities= new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getRoleName()));});
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);


    }
}
