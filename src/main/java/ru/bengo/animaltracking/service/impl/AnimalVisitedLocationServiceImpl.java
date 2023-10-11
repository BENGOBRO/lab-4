package ru.bengo.animaltracking.service.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.dto.AnimalVisitedLocationDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.model.LifeStatus;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.AnimalVisitedLocationRepository;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.service.AnimalVisitedLocationService;
import ru.bengo.animaltracking.service.LocationService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final LocationService locationService;
    private final AnimalService animalService;

    @Override
    public AnimalVisitedLocation create(@NotNull @Positive Long animalId, @NotNull @Positive Long locationId) throws NotFoundException, BadRequestException {
        Location visitedLocation = locationService.get(locationId);
        Animal animal = animalService.get(animalId);
        var visitedLocations = animal.getVisitedLocations();
        if (animal.getLifeStatus().equals(LifeStatus.DEAD)) {
            throw new BadRequestException(Message.ANIMAL_IS_DEAD.getInfo());
        }
        if (animal.getChippingLocation().getId().equals(locationId) && visitedLocations.isEmpty()) {
            throw new BadRequestException(Message.ANIMAL_IN_CHIPPING_LOCATION.getInfo());
        }
        if (animal.getChippingLocation().equals(visitedLocation)) {
            throw new BadRequestException(Message.CHIPPING_LOCATION_IS_VISITED_LOCATION.getInfo());
        }

        int lastIndex = visitedLocations.size() - 1;
        if (!visitedLocations.isEmpty() && visitedLocations.get(lastIndex).getLocation().equals(visitedLocation)) {
            throw new BadRequestException(Message.ANIMAL_IS_ALREADY_LOCATED.getInfo());
        }

        return animalVisitedLocationRepository.save(convertToEntity(visitedLocation, animal));
    }

    @Override
    public List<AnimalVisitedLocation> search(@NotNull @Positive Long animalId, Date startDateTime,
                                              Date endDateTime, @Min(0) Integer from, @Min(1) Integer size) {
        List<AnimalVisitedLocation> animalVisitedLocations = animalVisitedLocationRepository.search(startDateTime, endDateTime);

        return animalVisitedLocations.stream().skip(from).limit(size).toList();
    }

    @Override
    public AnimalVisitedLocation update(@NotNull @Positive Long animalId, AnimalVisitedLocationDto animalVisitedLocationDto) throws NotFoundException, BadRequestException {
        Animal animal = animalService.get(animalId);
        AnimalVisitedLocation animalVisitedLocation = animalVisitedLocationRepository.findById(animalVisitedLocationDto.getId())
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_VISITED_LOCATION_NOT_FOUND.getInfo()));
        Location newVisitedLocation = locationService.get(animalVisitedLocationDto.getLocationPointId());
        var visitedLocations = animal.getVisitedLocations();
        if (!visitedLocations.contains(animalVisitedLocation)) {
            throw new NotFoundException(Message.ANIMAL_VISITED_LOCATION_NOT_FOUND.getInfo());
        }
        if (newVisitedLocation.equals(animal.getChippingLocation())) {
            throw new BadRequestException(Message.CHIPPING_LOCATION_IS_VISITED_LOCATION.getInfo());
        }
        animalVisitedLocation.setLocation(newVisitedLocation);
        return animalVisitedLocation;
    }

    @Override
    public void delete(Long animalId, Long locationId) {
        animalVisitedLocationRepository.deleteAnimalVisitedLocationByAnimal_IdAndLocation_Id(animalId, locationId);
    }

    private AnimalVisitedLocation convertToEntity(Location locationPoint, Animal animal) {
        return AnimalVisitedLocation.builder()
                .location(locationPoint)
                .animal(animal)
                .build();
    }

//    private boolean isNeighbour() {
//
//    }
}
