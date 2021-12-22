package com.swoqe.newsstand.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 1, max=255)
    @Pattern(regexp="^[a-zA-Zа-яА-Я]+$", message = "Only letters are allowed")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max=255)
    @Pattern(regexp="^[a-zA-Zа-яА-Я]+$", message = "Only letters are allowed")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Size(min = 1, max=255)
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max=255, message = "Password size must be 8 symbols at least")
    private String password;

    @NotBlank(message = "Repeat your password")
    private String matchingPassword;
}
