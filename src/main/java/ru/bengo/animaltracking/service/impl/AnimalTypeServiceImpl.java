package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalTypeDto;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.AnimalTypeRepository;
import ru.bengo.animaltracking.service.AnimalTypeService;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    @Override
    public AnimalType create(@Valid AnimalTypeDto animalTypeDto) throws ConflictException {
        if (isAnimalTypeWithTypeExist(animalTypeDto.type())) {
            throw new ConflictException(Message.ANIMAL_TYPE_EXIST.getInfo());
        }
        return animalTypeRepository.save(convertToEntity(animalTypeDto));
    }

    @Override
    public AnimalType get(@NotNull @Positive Long id) throws NotFoundException {
        return animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo()));
    }

    @Override
    public AnimalType update(Long id, AnimalTypeDto animalTypeDto) throws NotFoundException, ConflictException {
        animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo()));

        if (isAnimalTypeWithTypeExist(animalTypeDto.type())) {
            throw new ConflictException(Message.ANIMAL_TYPE_EXIST.getInfo());
        }

        AnimalType animalType = convertToEntity(animalTypeDto);
        animalType.setId(id);
        return animalTypeRepository.save(animalType);
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws NotFoundException, BadRequestException {
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo()));

        if (!animalType.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.ANIMAL_TYPE_ASSOCIATION.getInfo());
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
