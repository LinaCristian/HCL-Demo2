package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TransactionResponseModel {
    public UUID id;
    public String createdAt;
    private UserTransactionResponseModel fromUser;
    private UserTransactionResponseModel toUser;
    private Double amount;
    private String details;
}
