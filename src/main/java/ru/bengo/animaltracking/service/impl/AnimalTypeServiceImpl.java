package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalTypeDto;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.AnimalTypeRepository;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private static final Logger log = LoggerFactory.getLogger(AnimalTypeServiceImpl.class);

    @Override
    public AnimalType create(@Valid AnimalTypeDto animalTypeDto) throws AnimalTypeAlreadyExist {
        if (isAnimalTypeWithTypeExist(animalTypeDto.type())) {
            throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPE_EXIST.getInfo());
        }

        return animalTypeRepository.save(convertToEntity(animalTypeDto));
    }

    @Override
    public AnimalType get(@NotNull @Positive Long id) throws AnimalTypeNotFoundException {
        Optional<AnimalType> foundAnimalType = animalTypeRepository.findById(id);

        if (foundAnimalType.isEmpty()) {
            throw new AnimalTypeNotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }

        return foundAnimalType.get();
    }

    @Override
    public AnimalType update(Long id, AnimalTypeDto animalTypeDto) throws AnimalTypeAlreadyExist, AnimalTypeNotFoundException {
        Optional<AnimalType> foundAnimalType = animalTypeRepository.findById(id);

        if (foundAnimalType.isEmpty()) {
            throw new AnimalTypeNotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }
        if (isAnimalTypeWithTypeExist(animalTypeDto.type())) {
            throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPE_EXIST.getInfo());
        }

        AnimalType animalType = convertToEntity(animalTypeDto);
        animalType.setId(id);
        return animalTypeRepository.save(animalType);
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws AnimalTypeNotFoundException {
        Optional<AnimalType> foundAnimalType = animalTypeRepository.findById(id);

        if (foundAnimalType.isEmpty()) {
            throw new AnimalTypeNotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo());
        }

        animalTypeRepository.deleteById(id);
    }

    private boolean isAnimalTypeWithTypeExist(String type) {
        return animalTypeRepository.findByType(type).isPresent();
    }

    private AnimalType convertToEntity(AnimalTypeDto animalTypeDto) {
        return AnimalType.builder()
                .type(animalTypeDto.type())
                .build();
    }
}
