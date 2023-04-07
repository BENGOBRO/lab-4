package ru.bengo.animaltracking.model;

public enum LifeStatus {
    ALIVE, DEAD;

    public static boolean isLifeStatus(String value) {
        for (LifeStatus lifeStatus: values()) {
            if (lifeStatus.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
