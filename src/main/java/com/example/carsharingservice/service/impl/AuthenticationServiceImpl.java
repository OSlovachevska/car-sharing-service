package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.UserService;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public User register(String email, String firstName, String lastName, String password) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(User.Role.CUSTOMER);
        user = userService.save(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Can't find user by login: " + email));
        String encodedPassword = passwordEncoder.encode(password);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new RuntimeException("Incorrect username or password!!!");
        }
        return user;
    }
}
