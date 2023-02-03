package ru.bengo.animaltracking.model;

import lombok.Data;

@Data
public class Account {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
