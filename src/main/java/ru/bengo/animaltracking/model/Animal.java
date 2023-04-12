package ru.bengo.animaltracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
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
    @JsonIgnore
    private List<AnimalType> animalTypes;

    @Transient
    @JsonProperty("animalTypes")
    private List<Long> animalTypesJson;

    private Double weight;

    private Double length;

    private Double height;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime chippingDateTime;

    private Integer chipperId;

    private Long chippingLocationId;

    @ManyToMany
    @JsonIgnore
    private List<Location> visitedLocations;

    @Transient
    @JsonProperty("visitedLocations")
    private List<Long> visitedLocationsJson;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deathDateTime;
}
