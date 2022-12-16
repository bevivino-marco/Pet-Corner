package com.petcorner.profileservice.profileservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User  {
    @Id
    private String id;

    @NonNull
    private String username;

    @NonNull
    private String password;


}
