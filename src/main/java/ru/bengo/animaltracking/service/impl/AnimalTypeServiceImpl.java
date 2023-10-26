package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.api.dto.AnimalTypeDto;
import ru.bengo.animaltracking.api.dto.mapper.AnimalTypeMapper;
import ru.bengo.animaltracking.store.entity.AnimalType;
import ru.bengo.animaltracking.api.exception.BadRequestException;
import ru.bengo.animaltracking.api.exception.ConflictException;
import ru.bengo.animaltracking.api.exception.NotFoundException;
import ru.bengo.animaltracking.api.model.Message;
import ru.bengo.animaltracking.store.repository.AnimalTypeRepository;
import ru.bengo.animaltracking.service.AnimalTypeService;

@Service
@Validated
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;

    @Override
    public AnimalType create(@Valid AnimalTypeDto animalTypeDto) {
        isAnimalTypeExist(animalTypeDto.getType());
        return animalTypeRepository.save(animalTypeMapper.toEntity(animalTypeDto));
    }

    @Override
    public AnimalType get(@NotNull @Positive Long id) throws NotFoundException {
        return animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ANIMAL_TYPE_NOT_FOUND.getInfo()));
    }

    @Override
    public AnimalType update(Long id, AnimalTypeDto animalTypeDto) {
        var animalType = get(id);
        isAnimalTypeExist(animalType.getType());
        animalType.setType(animalTypeDto.getType());
        return animalTypeRepository.save(animalType);
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        var animalType = get(id);
        if (!animalType.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.ANIMAL_TYPE_ASSOCIATION.getInfo());
        }
        animalTypeRepository.deleteById(id);
    }

    private void isAnimalTypeExist(String type) {
        animalTypeRepository.findByType(type)
                .ifPresent(animalType -> {
                    throw new ConflictException(Message.ANIMAL_TYPE_EXIST.getInfo());
                });
    }
}
