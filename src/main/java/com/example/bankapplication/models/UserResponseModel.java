package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class UserResponseModel {
    private UUID id;
    private String createdAt;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
    private List<AccountResponseModel> accounts;
}
