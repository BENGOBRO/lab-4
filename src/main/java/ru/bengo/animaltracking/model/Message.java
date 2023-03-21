package ru.bengo.animaltracking.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Message {
    ACCOUNT_EXIST("There is an account with that email address");

    private final String info;

    public String getInfo() {
        return info;
    }
}
