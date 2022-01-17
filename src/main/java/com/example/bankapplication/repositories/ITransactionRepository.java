package com.example.bankapplication.repositories;

import com.example.bankapplication.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, UUID>
{
    @Query("from Transaction t join t.from u join u.accounts a where a.id = :accountUUID " +
            "AND DATE_FORMAT(t.createdAt, '%b') = :month AND DATE_FORMAT(t.createdAt, '%Y') = :year")
    List<Transaction> getByMonthAndYearAndAccount(String month, String year, UUID accountUUID);
}
