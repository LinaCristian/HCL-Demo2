package com.example.bankapplication.services.impl;

import com.example.bankapplication.models.TransactionCreateModel;
import com.example.bankapplication.models.TransactionResponseModel;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    TransactionResponseModel createTransfer(TransactionCreateModel transactionModel);
    List<TransactionResponseModel> getTransactions(String month, String year, UUID accountUUID);
}
