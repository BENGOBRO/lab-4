package ru.bengo.animaltracking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<AnimalType> animalTypes;

    @NotNull
    @Positive
    private Double weight;

    @NotNull
    @Positive
    private Double length;

    @NotNull
    @Positive
    private Double height;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime chippingDateTime;

    @NotNull
    @Positive
    private Integer chipperId;

    private Long chippingLocationId;

    @ManyToMany
    private List<Location> visitedLocations;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deathDateTime;
}
