package com.petcorner.adopt.adoptservice.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Animal {
    @Id
    private Long id;
    private String name;
    private int age;
    private String size;
    private String kennel;

    public Animal() {
    }

    public Animal(Long id, String name, int age, String size, String kennel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.size = size;
        this.kennel = kennel;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getKennel() {
        return kennel;
    }

    public void setKennel(String kennel) {
        this.kennel = kennel;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", size='" + size + '\'' +
                ", kennel='" + kennel + '\'' +
                '}';
    }
}
