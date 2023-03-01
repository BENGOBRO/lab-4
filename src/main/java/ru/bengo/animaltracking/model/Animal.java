package ru.bengo.animaltracking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double weight;
    private Double length;
    private Double height;
    private Gender gender;
//    private LifeStatus lifeStatus;

}
