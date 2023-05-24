package com.petcorner.animalsitting.animalsitting.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Sitter {
    @Id
    @SequenceGenerator(
            name = "sitters_id_sequence",
            sequenceName= "animals_id_sequence"
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sitters_id_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String locality;
    private String personalDescription;
    private String animalsAllowed;
    private int sizeAllowed;
    private String serviceOffered;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT")
    private String image;

    public Sitter(String name, String surname, int age, String locality, String personalDescription, String animalsAllowed, int sizeAllowed, String serviceOffered,String email, String image) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.locality = locality;
        this.personalDescription = personalDescription;
        this.animalsAllowed = animalsAllowed;
        this.sizeAllowed = sizeAllowed;
        this.serviceOffered = serviceOffered;
        this.email=email;
        this.image=image;
    }

    public String getServiceOffered() {
        return serviceOffered;
    }

    public void setServiceOffered(String serviceOffered) {
        this.serviceOffered = serviceOffered;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personalDescription) {
        this.personalDescription = personalDescription;
    }

    public String getAnimalsAllowed() {
        return animalsAllowed;
    }

    public void setAnimalsAllowed(String animalsAllowed) {
        this.animalsAllowed = animalsAllowed;
    }

    public int getSizeAllowed() {
        return sizeAllowed;
    }

    public void setSizeAllowed(int sizeAllowed) {
        this.sizeAllowed = sizeAllowed;
    }

    @Override
    public String toString() {

        return "Sitter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", locality='" + locality + '\'' +
                ", personal description='" + personalDescription + '\'' +
                ", animals allowed='" + animalsAllowed + '\'' +
                ", size allowed='" + sizeAllowed + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
