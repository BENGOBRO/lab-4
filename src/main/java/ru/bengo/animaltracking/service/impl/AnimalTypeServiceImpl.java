package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
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
    public AnimalType create(@Valid AnimalType animalType) throws AnimalTypeAlreadyExist {
        log.warn(">> Type here");
        if (isAnimalTypeWithTypeExist(animalType.getType())) {
            throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPE_EXIST.getInfo());
        }
        log.warn("I am here");
        return animalTypeRepository.save(animalType);
    }

    @Override
    public Optional<AnimalType> get(@NotNull @Positive Long id) {
        return animalTypeRepository.findById(id);
    }

    @Override
    public AnimalType update(Long id, AnimalType animalType) throws AnimalTypeAlreadyExist {
        Optional<AnimalType> foundAnimalType = animalTypeRepository.findById(id);

        if (foundAnimalType.isPresent()) {
            if (isAnimalTypeWithTypeExist(animalType.getType())) {
                throw new AnimalTypeAlreadyExist(Message.ANIMAL_TYPE_EXIST.getInfo());
            }
            animalType.setId(id);
            return animalTypeRepository.save(animalType);
        }

        return null;
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        animalTypeRepository.deleteById(id);
    }

    private boolean isAnimalTypeWithTypeExist(String type) {
        return animalTypeRepository.findByType(type).isPresent();
    }
}
