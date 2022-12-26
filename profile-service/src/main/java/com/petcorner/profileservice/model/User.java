package com.petcorner.profileservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="application_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
    private String cod_fisc;
    private String piva;
    private String country;
    private String city;
    private String address;

}
