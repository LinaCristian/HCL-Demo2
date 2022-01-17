package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class UserTransactionResponseModel {
    private UUID id;
    private String createdAt;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
}
