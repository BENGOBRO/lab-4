package ru.bengo.animaltracking.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    Integer id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Email
    @NotBlank
    String email;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    String password;
}

