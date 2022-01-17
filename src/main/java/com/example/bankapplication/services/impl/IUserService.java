package com.example.bankapplication.services.impl;

import com.example.bankapplication.entities.User;
import com.example.bankapplication.models.PaggingModel;
import com.example.bankapplication.models.UserRegistrationModel;
import com.example.bankapplication.models.UserResponseModel;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponseModel saveUser(UserRegistrationModel user) throws Exception;
    List<UserResponseModel> getAllUsers(PaggingModel pagging);
    void deleteUser(UUID id);
    User getUserById(UUID id);
    User updateUserAge(User user, UUID id);
    List<User> getUserByName(String name);
    List<User> getUserByFirstName(String firstName);
}
