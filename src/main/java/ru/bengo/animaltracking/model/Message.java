package ru.bengo.animaltracking.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {
    ACCOUNT_EXIST("There is an account with that email address"),
    LOCATION_EXIST("There is a location with that latitude and longitude"),
    ANIMAL_TYPE_EXIST("There is an animal type with that type"),
    ANIMAL_TYPES_EXISTS_WITH_NEW_OLD_TYPES("The animal with animalId already has types with oldTypeId and newTypeId"),
    ANIMAL_TYPES_HAS_DUPLICATES("Animal types array contains duplicates"),
    ANIMAL_TYPE_NOT_FOUND("Animal type not found"),
    CHIPPER_ID_NOT_FOUND("Account with this chipper id not found"),
    CHIPPING_LOCATION_ID_NOT_FOUND("Chipping location id not found"),
    ANIMAL_NOT_FOUND("Animal not found"),
    UPDATE_DEAD_TO_ALIVE("This animal is already dead"),
    NEW_CHIPPING_LOCATION_ID_EQUALS_FIRST_VISITED_LOCATION("New chipping location id equals first visited location id"),
    NO_ACCESS("No rights to access"),
    ANIMAL_DOES_NOT_HAVE_TYPE("Animal type with oldTypeId does not exist for animal with animalId"),
    ANIMAL_TYPES_CONTAIN_NEW_ANIMAL_TYPE("The type of the animal with typeId already exists in the animal with animalId"),
    ACCOUNT_NOT_FOUND("Account with this id haven't found"),
    LOCATION_NOT_FOUND("Location with this id haven't found")
    ;

    private final String info;

    public String getInfo() {
        return info;
    }
}
