package com.example.bankapplication.controllers;

import com.example.bankapplication.models.TransactionCreateModel;
import com.example.bankapplication.models.TransactionResponseModel;
import com.example.bankapplication.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/transfer")
    public TransactionResponseModel transfer(@RequestBody TransactionCreateModel transactionModel) {
        return accountService.createTransfer(transactionModel);
    }

    @GetMapping
    public List<TransactionResponseModel> getTransactions(@RequestParam String month, @RequestParam String year,
                                                          @RequestParam UUID accountUUID) {
        return accountService.getTransactions(month, year, accountUUID);
    }
}
