package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new NoSuchElementException("can't get user with email " + username));
    }

    @Override
    public User updateUserRole(User.Role role, Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("can't get user with id " + id));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<User> findUserByRole(User.Role role) {
        return userRepository.getUserByRole(role);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserInfo(User user, String userName) {
        User userToUpdate = getByUsername(userName);
        user.setId(userToUpdate.getId());
        user.setEmail(userToUpdate.getEmail());
        User.Role role = userToUpdate.getRole();
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Can't find user with id: " + userId));
    }
}
