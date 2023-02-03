package ru.bengo.animaltracking.model;

import lombok.Data;

@Data
public class Animal {

    private Long id;
    private Double weight;
    private Double length;
    private Double height;
    private Gender gender;
    private LifeStatus lifeStatus;

}
