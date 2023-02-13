package com.petcorner.adopt.adoptservice.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Animal implements Serializable {
    @Id
    @SequenceGenerator(
            name = "animals_id_sequence",
            sequenceName = "animals_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "animals_id_sequence"
    )
    private Long id;
    private String name;
    private int age;

    @Column(name="animal_size")
    private int size;
    private String type;
    private String description;
    private String owner;
    private String provenance;
    @Column(unique = true, nullable = false)
    private String microchip;


    @Transient
    private String animalSize = "";

    public Animal() {
    }

    public Animal(String name, int age, String owner, int size, String provenance, String type, String description,String microchip) {

        this.name = name;
        this.age = age;
        this.owner = owner;
        this.size = size;
        this.type=type;
        this.description=description;
        this.provenance=provenance;
        this.microchip=microchip;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }


    @Override
    public String toString() {
        switch (size){
            case 0:
                animalSize="small";
                break;
            case 1:
                animalSize="medium";
                break;
            case 2:
                animalSize="large";
                break;

        }

        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", size='" + animalSize + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", kennel='" + owner + '\'' +
                ", provenance='" + provenance + '\'' +
                '}';
    }
}
