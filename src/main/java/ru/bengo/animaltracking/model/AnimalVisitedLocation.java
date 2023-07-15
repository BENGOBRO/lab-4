package ru.bengo.animaltracking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class AnimalVisitedLocation {

    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime dateTimeOfVisitLocationPoint;
    private Long locationPointId;


}
