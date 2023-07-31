package ru.bengo.animaltracking.model;

import jakarta.persistence.*;
import lombok.*;

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
}
