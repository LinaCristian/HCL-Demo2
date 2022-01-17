package com.example.bankapplication.services;

import com.example.bankapplication.entities.Account;
import com.example.bankapplication.entities.User;
import com.example.bankapplication.models.PaggingModel;
import com.example.bankapplication.models.UserRegistrationModel;
import com.example.bankapplication.models.UserResponseModel;
import com.example.bankapplication.repositories.IUserRepository;
import com.example.bankapplication.services.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    private Random random;

    public UserService() {
        this.random = new Random();
    }

    @Override
    public UserResponseModel saveUser(UserRegistrationModel user) throws ResponseStatusException {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(user.getEmail());

        if(!matcher.find())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email!");

        if(user.getAge() < 18 || user.getAge() > 80)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Age must be between 18 and 80!");

        if(user.getPhoneNumber().length() != 10)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone format (10 digits)!");

        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid name!");

        if(!userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName()).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Exists!");

        User userToSave = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .age(user.getAge())
                .build();

        userToSave.setAccounts(
                Collections.singletonList(
                Account.builder()
                        .balance(50.0)
                        .user(userToSave)
                        .accountNumber(String.format("%08d", random.nextInt(99999999)))
                        .build()));

        userRepository.save(userToSave);
        return userToSave.toResponse();
    }

    @Override
    public List<UserResponseModel> getAllUsers(PaggingModel pagging) {
        List<User> dbUsers = userRepository
                .findAll(PageRequest.of(
                        pagging.getPage() - 1,
                        pagging.getSize(),
                        Sort.by(pagging.getDesc()
                                        ? Sort.Direction.DESC
                                        : Sort.Direction.ASC,
                                pagging.getOrderBy() != null ? pagging.getOrderBy() : "id")))
                .getContent();

        return dbUsers.stream().map(User::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(UUID id) {
        User dbUser = userRepository.getById(id);
      /*  Optional<User> user = userRepository.findById(id);
        return user.orElse(null);*/
        return dbUser;
    }

    @Override
    public User updateUserAge(User user, UUID id) {
        User dbUser = getUserById(id);
        dbUser.setAge(user.getAge());
        return userRepository.save(dbUser);
    }

    @Override
    public List<User> getUserByName(String name) {
        return userRepository.getByName(name);
    }

    @Override
    public List<User> getUserByFirstName(String firstName) {
        return userRepository.findByFirstNameContainsOrderByIdDesc(firstName);
    }
}
