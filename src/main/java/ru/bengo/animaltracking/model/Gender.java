package ru.bengo.animaltracking.model;

public enum Gender {
    MALE, FEMALE, OTHER;

    public static boolean isGender(String value) {
        for (Gender gender: values()) {
            if (gender.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
