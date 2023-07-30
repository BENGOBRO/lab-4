package ru.bengo.animaltracking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "animals")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToMany
    @JsonIgnore
    @Column(nullable = false)
    @ToString.Exclude
    private Set<AnimalType> animalTypes;

    @Transient
    @JsonProperty("animalTypes")
    private List<Long> animalTypesJson;

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

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    private Set<Location> visitedLocations;

    @Transient
    @JsonProperty("visitedLocations")
    private List<Long> visitedLocationsJson;

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
