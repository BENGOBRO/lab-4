package ru.bengo.animaltracking.api.configuration;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.api.dto.AnimalDto;
import ru.bengo.animaltracking.api.dto.VisitedLocationDto;
import ru.bengo.animaltracking.store.entity.Account;
import ru.bengo.animaltracking.store.entity.Animal;
import ru.bengo.animaltracking.store.entity.VisitedLocation;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return new ModelMapper();
    }

}
