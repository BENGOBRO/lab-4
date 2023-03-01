package ru.bengo.animaltracking.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnimalDto {

    private List<Long> animalTypes;
    private Float weight;
    private Float length;
    private Float height;
    private String gender;
    private Integer chipperId;
    private Long ChipperLocationId;

}
