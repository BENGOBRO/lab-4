package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.exception.AnimalTypesHasDuplicatesException;
import ru.bengo.animaltracking.exception.ChipperIdNotFoundException;
import ru.bengo.animaltracking.exception.ChippingLocationIdNotFound;
import ru.bengo.animaltracking.model.*;
import ru.bengo.animaltracking.repository.AnimalRepository;
import ru.bengo.animaltracking.service.AccountService;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.service.AnimalTypeService;
import ru.bengo.animaltracking.service.LocationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalTypeService animalTypeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LocationService locationService;

    @Override
    public Optional<Animal> findById(@NotNull @Positive Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public List<Animal> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                         @Positive Integer chipperId, @Positive Long chippingLocationId,
                                         String lifeStatus, String gender,
                                         @Min(0) Integer from, @Min(1) Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size);
//        Page<Animal> page =
//                animalRepository.findByChippingDateTimeContainingOrDeathDateTimeContainingOrChipperIdContainingOrLifeStatusContainingOrGenderContaining(startDateTime, endDateTime, chipperId, lifeStatus, gender, pageRequest);
//        return page.getContent();
        return null;
    }

    @Override
    public Animal add(@Valid AnimalDto animalDto) throws AnimalTypesHasDuplicatesException, AnimalTypeNotFoundException, ChipperIdNotFoundException, ChippingLocationIdNotFound {

        List<Long> animalTypes = animalDto.getAnimalTypes();
        Integer chipperId = animalDto.getChipperId();
        Long chippingLocationId = animalDto.getChippingLocationId();
        if (hasAnimalTypesDuplicates(animalTypes)) {
            throw new AnimalTypesHasDuplicatesException(Message.ANIMAL_TYPES_HAS_DUPLICATES.getInfo());
        }
        if (!isAnimalTypeExist(animalTypes)) {
            throw new AnimalTypeNotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }
        if (!isChipperIdExist(chipperId)) {
            throw new ChipperIdNotFoundException(Message.CHIPPER_ID_NOT_FOUND.getInfo());
        }
        if (!isChippingLocationExist(chippingLocationId)) {
            throw new ChippingLocationIdNotFound(Message.CHIPPING_LOCATION_ID_NOT_FOUND.getInfo());
        }

        Animal animal = convertToEntity(animalDto);
        return animalRepository.save(animal);
    }

    @Override
    public Animal update(Long id, Animal animal) {
        return null;
    }

    private Animal convertToEntity(AnimalDto animalDto) {
        return new Animal();
    }
    private boolean isDead(LifeStatus lifeStatus) {
        return lifeStatus.name().equals("DEAD");
    }

    private boolean isAnimalTypeExist(List<Long> animalTypes) {
        for (Long id: animalTypes) {
            Optional<AnimalType> foundAnimalType = animalTypeService.get(id);
            if (foundAnimalType.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isChipperIdExist(Integer id) {
        return accountService.findById(id).isPresent();
    }

    private boolean isChippingLocationExist(Long id) {
        return locationService.getLocation(id).isPresent();
    }

    private boolean hasAnimalTypesDuplicates(List<Long> animalTypes) {
        return animalTypes.stream().distinct().count() < animalTypes.size();
    }
}
