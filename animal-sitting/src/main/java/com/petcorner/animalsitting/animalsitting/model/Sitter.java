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
    private String owner;
    private String locality;
    private String personalDescription;
    private String animalsAllowed;
    private int sizeAllowed;

    public Sitter(String name, String surname, int age, String owner, String locality, String personalDescription, String animalsAllowed, int sizeAllowed) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.owner = owner;
        this.locality = locality;
        this.personalDescription = personalDescription;
        this.animalsAllowed = animalsAllowed;
        this.sizeAllowed = sizeAllowed;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
