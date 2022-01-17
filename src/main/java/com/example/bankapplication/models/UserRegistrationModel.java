package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Builder
@Data
public class UserRegistrationModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
}
