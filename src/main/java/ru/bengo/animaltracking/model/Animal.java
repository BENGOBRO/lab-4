package ru.bengo.animaltracking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    private List<AnimalType> animalTypes;
    private Double weight;
    private Double length;
    private Double height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    private Long chipperId;
    @ManyToMany
    private List<Location> visitedLocations;
    private LocalDateTime deathDateTime;
}
