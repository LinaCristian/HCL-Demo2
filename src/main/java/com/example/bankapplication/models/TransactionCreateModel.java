package com.example.bankapplication.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TransactionCreateModel {
    private UUID fromUUID;
    private UUID toUUID;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String details;
}
