package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findUserByRole(User.Role role) {
        return userRepository.getUserByRole(role);
    }

    @Override
    public User updateUserInfo(User user, String username) {
        User userFromDb = findByEmail(username).orElseThrow(
                () -> new NoSuchElementException("Can`t update user"));
        user.setId(userFromDb.getId());
        user.setRole(userFromDb.getRole());
        user.setEmail(userFromDb.getEmail());
        user.setPassword(userFromDb.getPassword());
        user.setFirstName(userFromDb.getFirstName());
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can`t find user by id" + id));
    }
}
