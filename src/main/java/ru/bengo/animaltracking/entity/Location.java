package ru.bengo.animaltracking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private Double latitude;

    @Column(nullable = false, unique = true)
    private Double longitude;

    @OneToMany(mappedBy = "chippingLocation")
    @JsonIgnore
    @ToString.Exclude
    private List<Animal> animals;
}
