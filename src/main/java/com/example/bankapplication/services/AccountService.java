package com.example.bankapplication.services;

import com.example.bankapplication.entities.Account;
import com.example.bankapplication.entities.Transaction;
import com.example.bankapplication.entities.User;
import com.example.bankapplication.models.TransactionCreateModel;
import com.example.bankapplication.models.TransactionResponseModel;
import com.example.bankapplication.repositories.IAccountRepository;
import com.example.bankapplication.repositories.ITransactionRepository;
import com.example.bankapplication.repositories.IUserRepository;
import com.example.bankapplication.services.impl.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private ITransactionRepository transactionRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public TransactionResponseModel createTransfer(TransactionCreateModel transactionModel) {
        User fromUser = userRepository.findById(transactionModel.getFromUUID()).orElse(null);
        User toUser = userRepository.findById(transactionModel.getToUUID()).orElse(null);

        if(fromUser == null || toUser == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user!");

        if(fromUser.getId().equals(toUser.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot transfer to the same user!");

        if(transactionModel.getAmount() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be grater than 0!");

        Account fromUserAccount = fromUser.getAccounts().stream()
                .filter(p -> p.getAccountNumber().equals(transactionModel.getFromAccount())).findFirst().orElse(null);

        Account toUserAccount = toUser.getAccounts().stream()
                .filter(p -> p.getAccountNumber().equals(transactionModel.getToAccount())).findFirst().orElse(null);

        if(fromUserAccount == null || toUserAccount == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account!");

        if(fromUserAccount.getBalance() < transactionModel.getAmount())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No funds!");

        Transaction transaction = new Transaction(transactionModel, fromUser, toUser);
        transactionRepository.save(transaction);
        fromUserAccount.setBalance(fromUserAccount.getBalance() - transactionModel.getAmount());
        accountRepository.save(fromUserAccount);
        toUserAccount.setBalance(toUserAccount.getBalance() + transactionModel.getAmount());
        accountRepository.save(toUserAccount);
        return transaction.toResponse();
    }

    @Override
    public List<TransactionResponseModel> getTransactions(String month, String year, UUID accountUUID) {

        if(month == null || year == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date!");

        return transactionRepository.getByMonthAndYearAndAccount(month, year, accountUUID).stream()
                .map(Transaction::toResponse).collect(Collectors.toList());
    }
}
