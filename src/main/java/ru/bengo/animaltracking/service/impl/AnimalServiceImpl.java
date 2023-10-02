package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.model.*;
import ru.bengo.animaltracking.repository.AccountRepository;
import ru.bengo.animaltracking.repository.AnimalRepository;
import ru.bengo.animaltracking.repository.LocationRepository;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.service.AnimalTypeService;
import ru.bengo.animaltracking.service.LocationService;
import ru.bengo.animaltracking.service.interfaces.ThrowingFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private final ModelMapper modelMapper;

    private final AnimalRepository animalRepository;
    private final LocationRepository locationRepository;
    private final AccountRepository accountRepository;
    private final AnimalTypeService animalTypeService;
    private final LocationService locationService;


    @Override
    public Animal create(@Valid AnimalDto animalDto) throws AnimalTypesHasDuplicatesException,
            AnimalTypeNotFoundException, ChipperIdNotFoundException, ChippingLocationIdNotFound {

        var animalTypesIds = animalDto.getAnimalTypes();
        var animalTypes = getAnimalTypesById(animalTypesIds);

        List<Long> visitedLocationsIds = new ArrayList<>();
        var chippingLocationId = animalDto.getChippingLocationId();
        visitedLocationsIds.add(chippingLocationId);
        var visitedLocations = getVisitedLocationsById(visitedLocationsIds);

        var chipperId = animalDto.getChipperId();

        if (hasAnimalTypesDuplicates(animalTypesIds)) {
            throw new AnimalTypesHasDuplicatesException(Message.ANIMAL_TYPES_HAS_DUPLICATES.getInfo());
        }
        if (!isChipperIdExist(chipperId)) {
            throw new ChipperIdNotFoundException(Message.CHIPPER_ID_NOT_FOUND.getInfo());
        }
        if (!isChippingLocationExist(chippingLocationId)) {
            throw new ChippingLocationIdNotFound(Message.CHIPPING_LOCATION_ID_NOT_FOUND.getInfo());
        }
        Animal animal = animalRepository.save(convertToEntity(animalDto, animalTypes, visitedLocations));
        return animal;
    }


    @Override
    public Animal get(@NotNull @Positive Long id) throws AnimalNotFoundException {
        Optional<Animal> foundAnimal = animalRepository.findById(id);

        if (foundAnimal.isEmpty()) {
            throw new AnimalNotFoundException(Message.ANIMAL_NOT_FOUND.getInfo());
        }

        return foundAnimal.get();
    }


    @Override
    public Animal update(@NotNull @Positive Long id, @Valid AnimalDto animalDto) throws AnimalNotFoundException,
            UpdateDeadToAliveException, ChipperIdNotFoundException, ChippingLocationIdNotFound,
            NewChippingLocationIdEqualsFirstVisitedLocationIdException, LocationNotFoundException, AnimalTypeNotFoundException {

        var foundOptionalAnimal = animalRepository.findById(id);
        if (foundOptionalAnimal.isEmpty()) {
            throw new AnimalNotFoundException(Message.ANIMAL_NOT_FOUND.getInfo());
        }

        var foundAnimal = foundOptionalAnimal.get();
        var requestChipperId = animalDto.getChipperId();
        var requestChippingLocationId = animalDto.getChippingLocationId();
        var requestLifeStatusRequest = animalDto.getLifeStatus();
        var requestFirstVisitedLocationId = foundAnimal.getVisitedLocations().get(0).getId();

        if (isDead(foundAnimal.getLifeStatus())) {
            if (requestLifeStatusRequest.equals(LifeStatus.ALIVE.name())) {
                throw new UpdateDeadToAliveException(Message.UPDATE_DEAD_TO_ALIVE.getInfo());
            }
        }
        if (!isChipperIdExist(requestChipperId)) {
            throw new ChipperIdNotFoundException(Message.CHIPPER_ID_NOT_FOUND.getInfo());
        }
        if (!isChippingLocationExist(requestChippingLocationId)) {
            throw new ChippingLocationIdNotFound(Message.CHIPPING_LOCATION_ID_NOT_FOUND.getInfo());
        }
        if (isNewChippingLocationIdEqualsFirstVisitedLocationId(requestChippingLocationId,
                requestFirstVisitedLocationId)) {
            throw new NewChippingLocationIdEqualsFirstVisitedLocationIdException(
                    Message.NEW_CHIPPING_LOCATION_ID_EQUALS_FIRST_VISITED_LOCATION.getInfo());
        }

        var requestVisitedLocationsIds = animalDto.getVisitedLocations();
        var requestAnimalTypesIds = animalDto.getAnimalTypes();
        List<Location> visitedLocations = new ArrayList<>();
        List<AnimalType> animalTypes = new ArrayList<>();

        for (Long visitedLocationId: requestVisitedLocationsIds) {
            visitedLocations.add(locationService.get(visitedLocationId));
        }
        for (Long animalTypeId: requestAnimalTypesIds) {
            animalTypes.add(animalTypeService.get(animalTypeId));
        }

        Animal animal = convertToEntity(animalDto, animalTypes, visitedLocations);
        return animalRepository.save(animal);
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws AnimalNotFoundException {
        var foundOptionalAnimal = animalRepository.findById(id);
        if (foundOptionalAnimal.isEmpty()) {
            throw new AnimalNotFoundException(Message.ANIMAL_NOT_FOUND.getInfo());
        }
        animalRepository.deleteById(id);
    }

    @Override
    public List<Animal> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  @Positive Integer chipperId, @Positive Long chippingLocationId,
                                  String lifeStatus, String gender,
                                  @Min(0) Integer from, @Min(1) Integer size) {
        PageRequest pageRequest = PageRequest.ofSize(size + from);
        List<Animal> foundAnimals = animalRepository.search(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, pageRequest);
        return foundAnimals.stream().skip(from).toList();
    }

    @Override
    public Animal addAnimalTypeToAnimal(Long animalId, Long typeId) throws AnimalNotFoundException, AnimalTypeNotFoundException, AnimalTypesContainNewAnimalTypeException {
        return null;
    }

    @Override
    public Animal updateAnimalTypesInAnimal(Long animalId, TypeDto typeDto) throws AnimalNotFoundException, AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException, AnimalTypeAlreadyExist {
        return null;
    }
//
//    @Override
//    public AnimalDto addAnimalTypeToAnimal(@NotNull @Positive Long animalId,
//                                        @NotNull @Positive Long typeId) throws AnimalNotFoundException,
//            AnimalTypeNotFoundException, AnimalTypesContainNewAnimalTypeException {
//
//        var foundAnimal = animalRepository.findById(animalId);
//        if (foundAnimal.isEmpty()) {
//            throw new AnimalNotFoundException(Message.ANIMAL_NOT_FOUND.getInfo());
//        }
//
//        Animal animal = foundAnimal.get();
//        var animalTypes = animal.getAnimalTypes();
//        var animalTypesIds = getAnimalTypesIds(animalTypes);
//        if (isAnimalTypesContainNewAnimalTypeId(animalTypesIds, typeId)) {
//            throw new AnimalTypesContainNewAnimalTypeException(Message.ANIMAL_TYPES_CONTAIN_NEW_ANIMAL_TYPE.getInfo());
//        }
//
//        animalTypes.add(animalTypeService.get(typeId));
//        animal.setAnimalTypes(animalTypes);
//        var savedAnimal = animalRepository.save(animal);
//        var savedAnimalTypesIds = getAnimalTypesIds(savedAnimal.getAnimalTypes());
//        var savedAnimalVisitedLocationsIds = getVisitedLocationsIds(savedAnimal.getVisitedLocations());
//        return convertToDto(savedAnimal, savedAnimalTypesIds, savedAnimalVisitedLocationsIds);
//    }
//
//    @Override
//    public AnimalDto updateAnimalTypesInAnimal(@NotNull @Positive Long animalId,
//                                            @Valid TypeDto typeDto) throws AnimalNotFoundException,
//            AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException, AnimalTypeAlreadyExist {
//
//        Optional<Animal> foundAnimal = animalRepository.findById(animalId);
//        if (foundAnimal.isEmpty()) {
//            throw new AnimalNotFoundException(Message.ANIMAL_NOT_FOUND.getInfo());
//        }
//
//        var newTypeId = typeDto.newTypeId();
//        var oldTypeId = typeDto.oldTypeId();
//        var newType = animalTypeService.get(newTypeId);
//        var oldType = animalTypeService.get(oldTypeId);
//        if (!isAnimalTypeExist(oldTypeId)) {
//            throw new AnimalDoesNotHaveTypeException(Message.ANIMAL_DOES_NOT_HAVE_TYPE.getInfo());
//        }
//        if (isAnimalTypeExist(newTypeId)) {
//            throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPE_EXIST.getInfo());
//        }
//        if (isAnimalTypesExist(List.of(newTypeId, oldTypeId))) {
//            throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPES_EXISTS_WITH_NEW_OLD_TYPES.getInfo());
//        }
//
//        Animal animal = foundAnimal.get();
//        List<AnimalType> animalTypes = animal.getAnimalTypes();
//        animalTypes.remove(oldType);
//        animalTypes.add(newType);
//        animal.setAnimalTypes(animalTypes);
//        var savedAnimal = animalRepository.save(animal);
//        var savedAnimalTypesIds = getAnimalTypesIds(savedAnimal.getAnimalTypes());
//        var savedAnimalVisitedLocationsIds = getVisitedLocationsIds(savedAnimal.getVisitedLocations());
//        return convertToDto(savedAnimal, savedAnimalTypesIds, savedAnimalVisitedLocationsIds);
//    }
//
    private Animal convertToEntity(AnimalDto animalDto, List<AnimalType> animalTypes, List<Location> visitedLocations) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        animal.setAnimalTypes(animalTypes);
        return animal;
    }

    private static <T, R> Function<T, R> throwingFunctionWrapper(
            ThrowingFunction<T, R, Exception> throwingFunction) {
        return i -> {
            try {
                return throwingFunction.apply(i);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }

    private List<AnimalType> getAnimalTypesById(List<Long> animalTypesIds) {
        return animalTypesIds.stream().map(throwingFunctionWrapper(animalTypeService::get)).toList();
    }

    private List<Long> getAnimalTypesIds(List<AnimalType> animalTypes) {
        return animalTypes.stream().map(AnimalType::getId).toList();
    }

    private List<Location> getVisitedLocationsById(List<Long> visitedLocationsIds) {
        List<Location> visitedLocations = new ArrayList<>();
        for (Long id: visitedLocationsIds) {
            Location visitedLocation;
        }
        return visitedLocations;
    }

    private List<Long> getVisitedLocationsIds(List<Location> visitedLocations) {
        return visitedLocations.stream().map(Location::getId).toList();
    }

    private boolean isDead(LifeStatus lifeStatus) {
        return lifeStatus.name().equals(LifeStatus.DEAD.name());
    }

    private boolean isAnimalTypesExist(List<Long> animalTypes) throws AnimalTypeNotFoundException {
        for (var id: animalTypes) {
            var foundAnimalType = animalTypeService.get(id);
        }
        return true;
    }

    private boolean isAnimalTypeExist(Long animalType) throws AnimalTypeNotFoundException {
        return isAnimalTypesExist(List.of(animalType));
    }

    private boolean isChipperIdExist(Integer id) {
        return accountRepository.findById(id).isPresent();
    }

    private boolean isChippingLocationExist(Long id) {
        return locationRepository.findById(id).isPresent();
    }

    private boolean hasAnimalTypesDuplicates(List<Long> animalTypes) {
        return animalTypes.stream().distinct().count() < animalTypes.size();
    }

    private boolean isNewChippingLocationIdEqualsFirstVisitedLocationId(Long newChippingLocationId,
                                                                        Long firstVisitedLocationId) {
        return newChippingLocationId.equals(firstVisitedLocationId);
    }

    private boolean isAnimalTypesContainNewAnimalTypeId(List<Long> animalTypes, Long newAnimalType) {
        return animalTypes.stream().anyMatch((newType) -> newType.equals(newAnimalType));
    }
}
