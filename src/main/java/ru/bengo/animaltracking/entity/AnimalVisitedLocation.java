package ru.bengo.animaltracking.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.bengo.animaltracking.dto.AnimalDto;

import java.util.Date;

@Entity
@Table(name = "animal_visited_locations")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnimalVisitedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal")
    private Animal animal;

    @Column(nullable = false)
    private Date dateTimeOfVisitLocationPoint;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @PrePersist
    void onCreate() {
        this.dateTimeOfVisitLocationPoint = new Date();
    }
}
