package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import java.util.Optional;
import javax.naming.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private static final String EMAIL = "test@gmail.com";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String PASSWORD = "password";

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    @DisplayName("Register a new user")
    public void should_RegisterNewUser() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setPassword(PASSWORD);
        user.setRole(User.Role.CUSTOMER);

        when(userService.save(any(User.class))).thenReturn(user);

        User registeredUser = authenticationService.register(EMAIL,
                FIRST_NAME, LAST_NAME, PASSWORD);
        Mockito.verify(userService, Mockito.times(1)).save(any(User.class));

        assertNotNull(registeredUser);
        assertEquals(EMAIL, registeredUser.getEmail());
        assertEquals(FIRST_NAME, registeredUser.getFirstName());
        assertEquals(LAST_NAME, registeredUser.getLastName());
        assertEquals(User.Role.CUSTOMER, registeredUser.getRole());
    }

    @Test
    @DisplayName("Login with valid credentials")
    public void should_LoginWithValidCredentials() throws AuthenticationException {
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);

        User loggedInUser = authenticationService.login(EMAIL, PASSWORD);

        assertNotNull(loggedInUser);
        assertEquals(EMAIL, loggedInUser.getEmail());
    }

    @Test
    @DisplayName("Login with invalid credentials")
    public void should_NotLoginWithInvalidCredentials() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class,
                () -> authenticationService.login(EMAIL, PASSWORD));
    }
}
