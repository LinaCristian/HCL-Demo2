package com.example.bankapplication.repositories;

import com.example.bankapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID>
{
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByFirstNameContainsOrderByIdDesc(String firstName);

    @Query("from User where firstName LIKE CONCAT('%', :name, '%') OR lastName LIKE CONCAT('%', :name, '%')")
    List<User> getByName(String name);
}
