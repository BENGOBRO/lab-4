package ru.bengo.animaltracking.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {
    ACCOUNT_EXIST("There is an account with that email address"),
    LOCATION_EXIST("There is a location with that latitude and longitude"),
    ANIMAL_TYPE_EXIST("There is an animal type with that type"),
    ANIMAL_TYPES_HAS_DUPLICATES("Animal types array contains duplicates"),
    ANIMAL_TYPE_NOT_FOUND("Animal type not found"),
    CHIPPER_ID_NOT_FOUND("Account with this chipper id not found"),
    CHIPPING_LOCATION_ID_NOT_FOUND("Chipping locationd id not found"),
    NO_ACCESS("No rights to access");

    private final String info;

    public String getInfo() {
        return info;
    }
}
