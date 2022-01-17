package com.example.bankapplication.entities;

import com.example.bankapplication.models.TransactionCreateModel;
import com.example.bankapplication.models.TransactionResponseModel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction extends BaseEntity
{
    @Min(0)
    private Double amount;
    @Size(min = 3, max = 50)
    private String details;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User to;

    public Transaction(TransactionCreateModel transactionModel, User from, User to) {
        super();
        this.amount = transactionModel.getAmount();
        this.details = transactionModel.getDetails();
        this.from = from;
        this.to = to;
    }

    public TransactionResponseModel toResponse() {
        return TransactionResponseModel.builder()
                .id(this.id)
                .createdAt(new SimpleDateFormat("yyyy-MM-dd").format(this.createdAt))
                .amount(this.amount)
                .details(this.details)
                .fromUser(this.from.toTransactionResponse())
                .toUser(this.to.toTransactionResponse())
                .build();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
