package com.petcorner.profileservice.profileservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PetCornerUser {
    @Id
    private String id;

    @NonNull
    private String username;

    @NonNull
    private String password;


}
