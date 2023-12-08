package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;

public interface UserService {

    User save(User user);

    User getByUsername(String username);

    List<User> findUserByRole(User.Role role);

    User updateUserInfo(User user, String username);

    User getById(Long id);
}
