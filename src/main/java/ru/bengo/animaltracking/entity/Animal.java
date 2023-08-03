package ru.bengo.animaltracking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import ru.bengo.animaltracking.model.Gender;
import ru.bengo.animaltracking.model.LifeStatus;

import java.time.LocalDateTime;
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

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime chippingDateTime;

    @Column(nullable = false)
    private Integer chipperId;

    @Column(nullable = false)
    private Long chippingLocationId;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Location> visitedLocations;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deathDateTime;

    @PrePersist
    void onCreate() {
        this.lifeStatus = LifeStatus.ALIVE;
        this.chippingDateTime = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        if (this.lifeStatus.equals(LifeStatus.DEAD)) {
            this.deathDateTime = LocalDateTime.now();
        }
    }
}
