package ru.bengo.animaltracking.store.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.bengo.animaltracking.api.model.Gender;
import ru.bengo.animaltracking.api.model.LifeStatus;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "animals")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "animals_animal_types",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id")
    )
    @ToString.Exclude
    private List<AnimalType> animalTypes;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double length;

    @Column(nullable = false)
    private Double height;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LifeStatus lifeStatus;

    @Column(nullable = false)
    private Date chippingDateTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account chipper;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Location chippingLocation;

    @OneToMany(mappedBy = "animal")
    @ToString.Exclude
    private List<VisitedLocation> visitedLocations;

    private Date deathDateTime;

    @PrePersist
    void onCreate() {
        this.lifeStatus = LifeStatus.ALIVE;
        this.chippingDateTime = new Date();
    }

    @PreUpdate
    void onUpdate() {
        if (this.lifeStatus.equals(LifeStatus.DEAD)) {
            this.deathDateTime = new Date();
        }
    }
}
