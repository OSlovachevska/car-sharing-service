package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findUserByRole(User.Role role);

    User updateUserInfo(User user, String username);

    User getById(Long id);
}
