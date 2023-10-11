package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.model.Gender;
import ru.bengo.animaltracking.model.LifeStatus;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.AnimalRepository;
import ru.bengo.animaltracking.service.AccountService;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.service.AnimalTypeService;
import ru.bengo.animaltracking.service.LocationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private final ModelMapper modelMapper;
    private final AnimalRepository animalRepository;
    private final AccountService accountService;
    private final AnimalTypeService animalTypeService;
    private final LocationService locationService;

    @Override
    public Animal create(@Valid AnimalDto animalDto) throws ConflictException, NotFoundException, BadRequestException {
        var animalTypesIds = animalDto.getAnimalTypesIds();
        if (animalTypesIds == null || animalTypesIds.isEmpty()) {
            throw new BadRequestException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }
        if (hasAnimalTypesDuplicates(animalTypesIds)) {
            throw new ConflictException(Message.ANIMAL_TYPES_HAS_DUPLICATES.getInfo());
        }

        var chippingLocation = locationService.get(animalDto.getChippingLocationId());
        var chipper = accountService.get(animalDto.getChipperId());
        var animalTypes = getAnimalTypes(animalTypesIds);

        return animalRepository.save(convertToEntity(animalDto, animalTypes, chipper, chippingLocation));
    }


    @Override
    public Animal get(@NotNull @Positive Long id) throws NotFoundException {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_NOT_FOUND.getInfo()));
    }


    @Override
    public Animal update(@NotNull @Positive Long animalId, @Valid AnimalDto animalDto) throws NotFoundException, BadRequestException {
        var animal = get(animalId);
        var newChipper = accountService.get(animalDto.getChipperId());
        var newChippingLocation = locationService.get(animalDto.getChippingLocationId());

        var newLifeStatus = animalDto.getLifeStatus();
        if (isDead(animal.getLifeStatus())) {
            if (newLifeStatus.equals(LifeStatus.ALIVE.name())) {
                throw new BadRequestException(Message.UPDATE_DEAD_TO_ALIVE.getInfo());
            }
        }

        if (!animal.getVisitedLocations().isEmpty()) {
            var firstVisitedLocationId = animal.getVisitedLocations().get(0).getId();
            if (firstVisitedLocationId.equals(newChippingLocation.getId())) {
                throw new BadRequestException(
                        Message.NEW_CHIPPING_LOCATION_ID_EQUALS_FIRST_VISITED_LOCATION.getInfo());
            }
        }

        animal.setWeight(animalDto.getWeight());
        animal.setLength(animalDto.getLength());
        animal.setHeight(animalDto.getHeight());
        animal.setGender(Gender.valueOf(animalDto.getGender()));
        animal.setLifeStatus(LifeStatus.valueOf(newLifeStatus));
        animal.setChipper(newChipper);
        animal.setChippingLocation(newChippingLocation);
        return animalRepository.save(animal);
    }

    @Override
    public void delete(@NotNull @Positive Long animalId) throws NotFoundException, BadRequestException {
        Animal animal = get(animalId);

        if (!animal.getVisitedLocations().isEmpty()) {
            throw new BadRequestException(Message.ANIMAL_ASSOCIATION.getInfo());
        }

        animalRepository.deleteById(animalId);
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
    public Animal addAnimalTypeToAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId) throws NotFoundException, ConflictException {
        Animal animal = get(animalId);
        AnimalType animalType = animalTypeService.get(typeId);
        List<AnimalType> animalTypes = animal.getAnimalTypes();
        if (animalTypes.contains(animalType)) {
            throw new ConflictException(Message.ANIMAL_TYPES_CONTAIN_NEW_ANIMAL_TYPE.getInfo());
        }
        animalTypes.add(animalType);
        animal.setAnimalTypes(animalTypes);
        return animalRepository.save(animal);
    }

    @Override
    public Animal updateAnimalTypeInAnimal(@NotNull @Positive Long animalId, @Valid TypeDto typeDto) throws NotFoundException, ConflictException {
        Animal animal = get(animalId);
        AnimalType oldType = animalTypeService.get(typeDto.oldTypeId());
        AnimalType newType = animalTypeService.get(typeDto.newTypeId());
        List<AnimalType> animalTypes = animal.getAnimalTypes();
        if (!animalTypes.contains(oldType)) {
            throw new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }
        if (animalTypes.contains(newType) || (animalTypes.contains(oldType) && animalTypes.contains(newType))) {
            throw new ConflictException(Message.ANIMAL_TYPES_CONTAIN_NEW_ANIMAL_TYPE.getInfo());
        }
        animalTypes.remove(oldType);
        animalTypes.add(newType);
        animal.setAnimalTypes(animalTypes);
        return animal;
    }

    @Override
    public Animal deleteAnimalTypeInAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId) throws NotFoundException, BadRequestException {
        Animal animal = get(animalId);
        AnimalType animalType = animalTypeService.get(typeId);
        List<AnimalType> animalTypes = animal.getAnimalTypes();
        if (animalTypes.size() == 1 && animalTypes.get(0).equals(animalType)) {
            throw new BadRequestException(Message.LAST_ANIMAL_TYPE.getInfo());
        }
        if (!animalTypes.contains(animalType)) {
            throw new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }
        animalTypes.remove(animalType);
        animal.setAnimalTypes(animalTypes);
        return animalRepository.save(animal);
    }

    private Animal convertToEntity(AnimalDto animalDto, List<AnimalType> animalTypes,
                                   Account chipper, Location chippingLocation) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        animal.setAnimalTypes(animalTypes);
        animal.setChipper(chipper);
        animal.setChippingLocation(chippingLocation);
        return animal;
    }

    private boolean isDead(LifeStatus lifeStatus) {
        return lifeStatus.name().equals(LifeStatus.DEAD.name());
    }

    private List<AnimalType> getAnimalTypes(List<Long> animalTypesIds) throws NotFoundException {
        List<AnimalType> animalTypes = new ArrayList<>();
        for (var id: animalTypesIds) {
            animalTypes.add(animalTypeService.get(id));
        }
        return animalTypes;
    }

    private boolean hasAnimalTypesDuplicates(List<Long> animalTypes) {
        return animalTypes.stream().distinct().count() < animalTypes.size();
    }
}
