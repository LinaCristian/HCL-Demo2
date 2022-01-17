package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccountResponseModel {
    private UUID id;
    private String accountNumber;
    private String createdAt;
    private Double balance;
}
