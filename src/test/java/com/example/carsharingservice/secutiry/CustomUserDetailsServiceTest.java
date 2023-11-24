package com.example.carsharingservice.secutiry;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Load user by username")
    public void loadUserByUsername_Ok() {
        String email = "example@gmail.com";
        User example = new User();
        example.setEmail(email);
        example.setPassword("example");
        example.setRole(User.Role.CUSTOMER);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(example));

        UserDetails actual = customUserDetailsService.loadUserByUsername(email);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(email, actual.getUsername());
        Assertions.assertEquals("example", actual.getPassword());
    }

    @Test
    @DisplayName("Can`t find user by username")
    void loadUserByUsername_NotOk() {
        String email = "example@gmail.com";
        User example = new User();
        example.setEmail(email);
        example.setPassword("example");
        example.setRole(User.Role.CUSTOMER);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(example));

        try {
            customUserDetailsService.loadUserByUsername("bob@gmail.com");
        } catch (Exception e) {
            Assertions.assertEquals("Can`t find user by email", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive Exception");
    }
}
