package ru.bengo.animaltracking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animal_types")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnimalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;
}
