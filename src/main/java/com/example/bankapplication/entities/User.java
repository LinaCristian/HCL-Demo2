package com.example.bankapplication.entities;

import com.example.bankapplication.models.AccountResponseModel;
import com.example.bankapplication.models.UserResponseModel;
import com.example.bankapplication.models.UserTransactionResponseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "users")
public class User extends BaseEntity
{
    @Size(min = 3, max = 20)
    private String firstName;
    @Size(min = 3, max = 20)
    private String lastName;
    @Size(min = 10, max = 12)
    private String phoneNumber;
    @Email
    private String email;
    @Min(18)
    @Max(80)
    private int age;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Account> accounts = new ArrayList<>();

    public UserResponseModel toResponse() {
        return UserResponseModel.builder()
                .id(this.id)
                .createdAt(new SimpleDateFormat("yyyy-MM-dd").format(this.createdAt))
                .firstName(this.firstName)
                .lastName(this.lastName)
                .age(this.age)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .accounts(this.accounts.stream().map(p -> AccountResponseModel.builder()
                    .id(p.getId())
                    .createdAt(new SimpleDateFormat("yyyy-MM-dd").format(this.createdAt))
                    .balance(p.getBalance())
                    .accountNumber(p.getAccountNumber())
                    .build()).collect(Collectors.toList()))
                .build();
    }

    public UserTransactionResponseModel toTransactionResponse() {
        return UserTransactionResponseModel.builder()
                .id(this.id)
                .createdAt(new SimpleDateFormat("yyyy-MM-dd").format(this.createdAt))
                .firstName(this.firstName)
                .lastName(this.lastName)
                .age(this.age)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .build();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
